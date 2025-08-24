package com.zry.xiaohongshu.kv.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.kv.dto.req.AddNoteContentReqDTO;
import com.zry.xiaohongshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.zry.xiaohongshu.kv.dto.req.FindNoteContentReqDTO;
import com.zry.xiaohongshu.kv.dto.rsp.FindNoteContentRspDTO;

public interface NoteContentService {
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);
    Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO);
    Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO);
}
