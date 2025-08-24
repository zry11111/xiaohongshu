package com.zry.xiaohongshu.kv.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.kv.dto.req.AddNoteContentReqDTO;

public interface NoteContentService {
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);
}
