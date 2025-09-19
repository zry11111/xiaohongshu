package com.zry.xiaohongshu.note.biz.service;

import com.zry.framework.common.reponse.PageResponse;
import com.zry.xiaohongshu.note.biz.model.vo.FindProfileNotePageListReqVO;
import com.zry.xiaohongshu.note.biz.model.vo.FindProfileNoteRspVO;

public interface ProfileService {
    PageResponse<FindProfileNoteRspVO> findNoteList(FindProfileNotePageListReqVO findProfileNotePageListReqVO);
}
