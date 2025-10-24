package com.zry.xiaohongshu.note.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.note.biz.model.vo.*;

public interface NoteService {
    Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);
    Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO);
    Response<?> updateNote(UpdateNoteReqVO updateNoteReqVO);
    void deleteNoteLocalCache(Long noteId);
    Response<?> deleteNote(DeleteNoteReqVO deleteNoteReqVO);
    Response<?> updateVisible(UpdateNoteVisibleReqVO updateNoteVisibleReqVO);
    Response<?> topNote(TopNoteReqVO topNoteReqVO);
    Response<?> likeNote(LikeNoteReqVO likeNoteReqVO);
    Response<?> unlikeNote(UnlikeNoteReqVO unlikeNoteReqVO);
    Response<?> collectNote(CollectNoteReqVO collectNoteReqVO);
    Response<?> unCollectNote(UnCollectNoteReqVO unCollectNoteReqVO);
    Response<FindNoteIsLikedAndCollectedRspVO> isLikedAndCollectedData(FindNoteIsLikedAndCollectedReqVO findNoteIsLikedAndCollectedReqVO);
    Response<FindPublishedNoteListRspVO> findPublishedNoteList(FindPublishedNoteListReqVO findPublishedNoteListReqVO);

}


