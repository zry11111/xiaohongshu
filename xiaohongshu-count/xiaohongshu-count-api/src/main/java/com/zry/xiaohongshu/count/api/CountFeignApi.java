package com.zry.xiaohongshu.count.api;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.count.constant.ApiConstants;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdRspDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdsReqDTO;
import com.zry.xiaohongshu.count.dto.FindUserCountsByIdReqDTO;
import com.zry.xiaohongshu.count.dto.FindUserCountsByIdRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface CountFeignApi {

    String PREFIX = "/count";
    @PostMapping(value = PREFIX + "/user/data")
    Response<FindUserCountsByIdRspDTO> findUserCount(@RequestBody FindUserCountsByIdReqDTO findUserCountsByIdReqDTO);
    @PostMapping(value = PREFIX + "/notes/data")
    Response<List<FindNoteCountsByIdRspDTO>> findNotesCount(@RequestBody FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO);
}