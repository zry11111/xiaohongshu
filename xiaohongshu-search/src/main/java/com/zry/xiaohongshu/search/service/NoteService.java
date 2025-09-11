package com.zry.xiaohongshu.search.service;

import com.zry.framework.common.reponse.PageResponse;
import com.zry.xiaohongshu.search.model.vo.SearchNoteReqVO;
import com.zry.xiaohongshu.search.model.vo.SearchNoteRspVO;

public interface NoteService {
    PageResponse<SearchNoteRspVO> searchNote(SearchNoteReqVO searchNoteReqVO);
}
