package com.zry.xiaohongshu.note.biz.service;

import com.zry.framework.common.reponse.PageResponse;
import com.zry.xiaohongshu.note.biz.model.vo.FindDiscoverNotePageListReqVO;
import com.zry.xiaohongshu.note.biz.model.vo.FindDiscoverNoteRspVO;

public interface DiscoverService {
    PageResponse<FindDiscoverNoteRspVO> findNoteList(FindDiscoverNotePageListReqVO findDiscoverNoteListReqVO);
}
