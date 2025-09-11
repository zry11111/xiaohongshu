package com.zry.xiaohongshu.search.biz.controller;

import com.zry.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zry.framework.common.reponse.PageResponse;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.search.biz.model.vo.SearchNoteReqVO;
import com.zry.xiaohongshu.search.biz.model.vo.SearchNoteRspVO;
import com.zry.xiaohongshu.search.biz.service.NoteService;
import com.zry.xiaohongshu.search.dto.RebuildNoteDocumentReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Slf4j
public class NoteController {

    @Resource
    private NoteService noteService;

    @PostMapping("/note")
    @ApiOperationLog(description = "搜索笔记")
    public PageResponse<SearchNoteRspVO> searchNote(@RequestBody @Validated SearchNoteReqVO searchNoteReqVO) {
        return noteService.searchNote(searchNoteReqVO);
    }
    @PostMapping("/note/document/rebuild")
    @ApiOperationLog(description = "用户文档重建")
    public Response<Long> rebuildDocument(@Validated @RequestBody RebuildNoteDocumentReqDTO rebuildNoteDocumentReqDTO) {
        return noteService.rebuildDocument(rebuildNoteDocumentReqDTO);
    }

}