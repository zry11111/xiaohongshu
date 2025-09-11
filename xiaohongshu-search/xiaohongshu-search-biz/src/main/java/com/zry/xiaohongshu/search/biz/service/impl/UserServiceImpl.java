package com.zry.xiaohongshu.search.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.zry.framework.common.reponse.PageResponse;
import com.zry.framework.common.reponse.Response;
import com.zry.framework.common.util.NumberUtils;
import com.zry.xiaohongshu.search.biz.domain.mapper.SelectMapper;
import com.zry.xiaohongshu.search.biz.index.UserIndex;
import com.zry.xiaohongshu.search.biz.model.vo.SearchUserReqVO;
import com.zry.xiaohongshu.search.biz.model.vo.SearchUserRspVO;
import com.zry.xiaohongshu.search.biz.service.UserService;
import com.zry.xiaohongshu.search.dto.RebuildUserDocumentReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private SelectMapper selectMapper;
    @Override
    public PageResponse<SearchUserRspVO> searchUser(SearchUserReqVO searchUserReqVO) {

        String keyword = searchUserReqVO.getKeyword();
        Integer pageNo = searchUserReqVO.getPageNo();

        SearchRequest searchRequest = new SearchRequest(UserIndex.NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.multiMatchQuery(
                keyword,UserIndex.FIELD_USER_NICKNAME,UserIndex.FIELD_USER_XIAOHONGSHU_ID));
        FieldSortBuilder sortBuilder = new FieldSortBuilder(UserIndex.FIELD_USER_FANS_TOTAL).order(SortOrder.DESC);

        sourceBuilder.sort(sortBuilder);

        int PageSize  = 10;
        int from = (pageNo-1)*PageSize;

        sourceBuilder.from(from);
        sourceBuilder.size(PageSize);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(UserIndex.FIELD_USER_NICKNAME)
                        .preTags("<strong>")
                        .postTags("</strong>");

        sourceBuilder.highlighter(highlightBuilder);
        // 将构建的查询条件设置到searchRequest中
        searchRequest.source(sourceBuilder);

        // 返参 VO 集合
        List<SearchUserRspVO> searchUserRspVOS = null;
        // 总文档数，默认为 0
        long total = 0;
        try {
            log.info("==> SearchRequest: {}", searchRequest);

            // 执行查询请求
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            // 处理搜索结果
            total = searchResponse.getHits().getTotalHits().value;
            log.info("==> 命中文档总数, hits: {}", total);

            searchUserRspVOS = Lists.newArrayList();

            // 获取搜索命中的文档列表
            SearchHits hits = searchResponse.getHits();

            for (SearchHit hit : hits) {
                log.info("==> 文档数据: {}", hit.getSourceAsString());

                // 获取文档的所有字段（以 Map 的形式返回）
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();

                // 提取特定字段值
                Long userId = ((Number) sourceAsMap.get(UserIndex.FIELD_USER_ID)).longValue();
                String nickname = (String) sourceAsMap.get(UserIndex.FIELD_USER_NICKNAME);
                String avatar = (String) sourceAsMap.get(UserIndex.FIELD_USER_AVATAR);
                String xiaohashuId = (String) sourceAsMap.get(UserIndex.FIELD_USER_XIAOHONGSHU_ID);
                Integer noteTotal = (Integer) sourceAsMap.get(UserIndex.FIELD_USER_NOTE_TOTAL);
                Integer fansTotal = (Integer) sourceAsMap.get(UserIndex.FIELD_USER_FANS_TOTAL);

                String highlightedNickname = null;
                if (CollUtil.isNotEmpty(hit.getHighlightFields())
                        && hit.getHighlightFields().containsKey(UserIndex.FIELD_USER_NICKNAME)) {
                    highlightedNickname = hit.getHighlightFields().get(UserIndex.FIELD_USER_NICKNAME).fragments()[0].string();
                }
                // 构建 VO 实体类
                SearchUserRspVO searchUserRspVO = SearchUserRspVO.builder()
                        .userId(userId)
                        .nickname(nickname)
                        .avatar(avatar)
                        .xiaohongshuId(xiaohashuId)
                        .noteTotal(noteTotal)
                        .fansTotal(NumberUtils.formatNumberString(fansTotal))
                        .highlightNickname(highlightedNickname)
                        .build();
                searchUserRspVOS.add(searchUserRspVO);
            }
        } catch (Exception e) {
            log.error("==> 查询 Elasticserach 异常: ", e);
        }

        return PageResponse.success(searchUserRspVOS, pageNo, total);
    }
    @Override
    public Response<Long> rebuildDocument(RebuildUserDocumentReqDTO rebuildUserDocumentReqDTO) {
        Long userId = rebuildUserDocumentReqDTO.getId();

        // 从数据库查询 Elasticsearch 索引数据
        List<Map<String, Object>> result = selectMapper.selectEsUserIndexData(userId);

        // 遍历查询结果，将每条记录同步到 Elasticsearch
        for (Map<String, Object> recordMap : result) {
            // 创建索引请求对象，指定索引名称
            IndexRequest indexRequest = new IndexRequest(UserIndex.NAME);
            // 设置文档的 ID，使用记录中的主键 “id” 字段值
            indexRequest.id((String.valueOf(recordMap.get(UserIndex.FIELD_USER_ID))));
            // 设置文档的内容，使用查询结果的记录数据
            indexRequest.source(recordMap);
            // 将数据写入 Elasticsearch 索引
            try {
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                log.error("==> 重建用户文档异常: ", e);
            }
        }
        return Response.success();
    }
}
