package com.zry.xiaohongshu.kv.biz.service.impl;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.kv.biz.domain.dataobject.NoteContentDO;
import com.zry.xiaohongshu.kv.biz.repository.NoteContentRepository;
import com.zry.xiaohongshu.kv.biz.service.NoteContentService;
import com.zry.xiaohongshu.kv.dto.req.AddNoteContentReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
