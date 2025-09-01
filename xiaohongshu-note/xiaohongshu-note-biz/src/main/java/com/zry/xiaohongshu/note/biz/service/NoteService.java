package com.zry.xiaohongshu.note.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.note.biz.model.vo.*;

public interface NoteService {
    Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);
    Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO);
    Response<?> updateNote(UpdateNoteReqVO updateNoteReqVO);
    void deleteNoteLocalCache(Long noteId);
    Response<?> deleteNote(DeleteNoteReqVO deleteNoteReqVO);
    Response<?> visibleOnlyMe(UpdateNoteVisibleOnlyMeReqVO updateNoteVisibleOnlyMeReqVO);
    Response<?> topNote(TopNoteReqVO topNoteReqVO);
    Response<?> likeNote(LikeNoteReqVO likeNoteReqVO);
}
