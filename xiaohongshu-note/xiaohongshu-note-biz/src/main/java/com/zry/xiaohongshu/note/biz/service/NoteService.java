package com.zry.xiaohongshu.note.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.note.biz.model.vo.FindNoteDetailReqVO;
import com.zry.xiaohongshu.note.biz.model.vo.FindNoteDetailRspVO;
import com.zry.xiaohongshu.note.biz.model.vo.PublishNoteReqVO;

public interface NoteService {
    Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);
    Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO);
}
