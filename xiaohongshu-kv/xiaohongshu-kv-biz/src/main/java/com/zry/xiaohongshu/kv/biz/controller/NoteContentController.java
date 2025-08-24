package com.zry.xiaohongshu.kv.biz.controller;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.kv.biz.service.NoteContentService;
import com.zry.xiaohongshu.kv.dto.req.AddNoteContentReqDTO;
import com.zry.xiaohongshu.kv.dto.req.FindNoteContentReqDTO;
import com.zry.xiaohongshu.kv.dto.rsp.FindNoteContentRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/kv")
public class NoteContentController {
    @Resource
    private NoteContentService noteContentService;
    @PostMapping(value = "/note/content/add")
    public Response<?> addNoteContent(@Validated @RequestBody AddNoteContentReqDTO addNoteContentReqDTO) {
        return noteContentService.addNoteContent(addNoteContentReqDTO);
    }
    @PostMapping(value = "/note/content/find")
    public Response<FindNoteContentRspDTO> findNoteContent(@Validated @RequestBody FindNoteContentReqDTO findNoteContentReqDTO) {
        return noteContentService.findNoteContent(findNoteContentReqDTO);
    }
}
