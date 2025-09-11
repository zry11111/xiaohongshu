package com.zry.xiaohongshu.search.biz.controller;

import com.zry.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zry.framework.common.reponse.PageResponse;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.search.biz.model.vo.SearchUserReqVO;
import com.zry.xiaohongshu.search.biz.model.vo.SearchUserRspVO;
import com.zry.xiaohongshu.search.biz.service.UserService;
import com.zry.xiaohongshu.search.dto.RebuildUserDocumentReqDTO;
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
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/user")
    @ApiOperationLog(description = "搜索用户")
    public PageResponse<SearchUserRspVO> searchUser(@RequestBody @Validated SearchUserReqVO searchUserReqVO) {
        return userService.searchUser(searchUserReqVO);
    }
    @PostMapping("/user/document/rebuild")
    @ApiOperationLog(description = "用户文档重建")
    public Response<Long> rebuildDocument(@Validated @RequestBody RebuildUserDocumentReqDTO rebuildUserDocumentReqDTO) {
        return userService.rebuildDocument(rebuildUserDocumentReqDTO);
    }

}