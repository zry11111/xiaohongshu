package com.zry.xiaohongshu.count.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.count.dto.FindNoteCountByIdReqDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountByIdRspDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdRspDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdsReqDTO;

import java.util.List;

public interface NoteCountService {
    Response<List<FindNoteCountsByIdRspDTO>> findNotesCountData(FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO);

    Response<FindNoteCountByIdRspDTO> findNoteCountData(FindNoteCountByIdReqDTO findNoteCountByIdReqDTO);
}
