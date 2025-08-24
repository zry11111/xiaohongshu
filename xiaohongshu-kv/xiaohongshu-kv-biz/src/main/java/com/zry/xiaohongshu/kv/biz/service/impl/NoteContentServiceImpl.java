package com.zry.xiaohongshu.kv.biz.service.impl;

import com.zry.framework.common.exception.BizException;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.kv.biz.domain.dataobject.NoteContentDO;
import com.zry.xiaohongshu.kv.biz.enums.ResponseCodeEnum;
import com.zry.xiaohongshu.kv.biz.repository.NoteContentRepository;
import com.zry.xiaohongshu.kv.biz.service.NoteContentService;
import com.zry.xiaohongshu.kv.dto.req.AddNoteContentReqDTO;
import com.zry.xiaohongshu.kv.dto.req.FindNoteContentReqDTO;
import com.zry.xiaohongshu.kv.dto.rsp.FindNoteContentRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class NoteContentServiceImpl implements NoteContentService {
    @Resource
    private NoteContentRepository noteContentRepository;
    @Override
    public Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO) {
        Long noteId = addNoteContentReqDTO.getNoteId();
        String content = addNoteContentReqDTO.getContent();
        NoteContentDO contentDO = NoteContentDO.builder()
                .id(UUID.randomUUID())
                .content(content)
                .build();
        noteContentRepository.save(contentDO);
        return Response.success();
    }

    @Override
    public Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO) {
        String noteId = findNoteContentReqDTO.getNoteId();
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString(noteId));
        if(!optional.isPresent()){
            throw new BizException(ResponseCodeEnum.NOTE_CONTENT_NOT_FOUND);
        }
        NoteContentDO noteContentDO = optional.get();
        FindNoteContentRspDTO findNoteContentRspDTO = FindNoteContentRspDTO.builder()
                .noteId(noteContentDO.getId())
                .content(noteContentDO.getContent())
                .build();
        return Response.success(findNoteContentRspDTO);
    }
}
