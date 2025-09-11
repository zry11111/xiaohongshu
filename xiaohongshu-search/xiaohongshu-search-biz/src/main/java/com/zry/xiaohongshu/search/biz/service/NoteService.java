package com.zry.xiaohongshu.search.biz.service;


import com.zry.framework.common.reponse.PageResponse;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.search.biz.model.vo.SearchNoteReqVO;
import com.zry.xiaohongshu.search.biz.model.vo.SearchNoteRspVO;
import com.zry.xiaohongshu.search.dto.RebuildNoteDocumentReqDTO;
import com.zry.xiaohongshu.search.dto.RebuildUserDocumentReqDTO;

public interface NoteService {
    PageResponse<SearchNoteRspVO> searchNote(SearchNoteReqVO searchNoteReqVO);
    /**
     * 重建笔记文档
     * @param rebuildNoteDocumentReqDTO
     * @return
     */
    Response<Long> rebuildDocument(RebuildNoteDocumentReqDTO rebuildNoteDocumentReqDTO);

}
