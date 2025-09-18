package com.zry.xiaohongshu.note.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.note.biz.domain.dataobject.ChannelDO;
import com.zry.xiaohongshu.note.biz.domain.mapper.ChannelDOMapper;
import com.zry.xiaohongshu.note.biz.model.vo.FindChannelRspVO;
import com.zry.xiaohongshu.note.biz.service.ChannelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService {
    @Resource
    private ChannelDOMapper channelDOMapper;
    @Override
    public Response<List<FindChannelRspVO>> findChannelList() {
        // TODO: 加缓存

        List<ChannelDO> channelDOS = channelDOMapper.selectAll();

        List<FindChannelRspVO> channelRspVOS = Lists.newArrayList();

        // 默认添加一个 “全部” 分类
         FindChannelRspVO allChannel = FindChannelRspVO.builder()
                 .id(0L)
                 .name("全部")
                 .build();
         channelRspVOS.add(allChannel);

        if (CollUtil.isNotEmpty(channelDOS)) {
            CollUtil.addAll(channelRspVOS, channelDOS.stream()
                    .map(channelDO -> FindChannelRspVO.builder()
                            .id(channelDO.getId())
                            .name(channelDO.getName())
                            .build())
                    .toList());
        }

        return Response.success(channelRspVOS);
    }
}
