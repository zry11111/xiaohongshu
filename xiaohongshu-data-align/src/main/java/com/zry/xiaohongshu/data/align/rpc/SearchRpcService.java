package com.zry.xiaohongshu.data.align.rpc;

import com.zry.xiaohongshu.search.api.SearchFeignApi;
import com.zry.xiaohongshu.search.dto.RebuildNoteDocumentReqDTO;
import com.zry.xiaohongshu.search.dto.RebuildUserDocumentReqDTO;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SearchRpcService {
    @Resource
    private SearchFeignApi searchFeignApi;
    /**
     * 调用重建笔记文档接口
     * @param noteId
     */
    public void rebuildNoteDocument(Long noteId) {
        RebuildNoteDocumentReqDTO rebuildNoteDocumentReqDTO = RebuildNoteDocumentReqDTO.builder()
                .id(noteId)
                .build();

        searchFeignApi.rebuildNoteDocument(rebuildNoteDocumentReqDTO);
    }

    /**
     * 调用重建用户文档接口
     * @param userId
     */
    public void rebuildUserDocument(Long userId) {
        RebuildUserDocumentReqDTO rebuildUserDocumentReqDTO = RebuildUserDocumentReqDTO.builder()
                .id(userId)
                .build();

        searchFeignApi.rebuildUserDocument(rebuildUserDocumentReqDTO);
    }
}
