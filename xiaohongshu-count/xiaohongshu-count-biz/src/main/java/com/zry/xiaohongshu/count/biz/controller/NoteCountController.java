package com.zry.xiaohongshu.count.biz.controller;

import com.zry.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.count.biz.service.NoteCountService;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdRspDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdsReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/count")
@Slf4j
public class NoteCountController {

    @Resource
    private NoteCountService noteCountService;

    @PostMapping(value = "/notes/data")
    @ApiOperationLog(description = "批量获取笔记计数数据")
    public Response<List<FindNoteCountsByIdRspDTO>> findNotesCountData(@Validated @RequestBody FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO) {
        return noteCountService.findNotesCountData(findNoteCountsByIdsReqDTO);
    }

}