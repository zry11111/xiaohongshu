package com.zry.xiaohongshu.kv.api;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.kv.constant.ApiConstants;
import com.zry.xiaohongshu.kv.dto.req.AddNoteContentReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ApiConstants.SERVICE_NAME)

public interface KeyValueFeignApi {
    String PREFIX = "/kv";
    @PostMapping(value = PREFIX + "/note/content/add")
    Response<?> addNoteContent(@RequestBody AddNoteContentReqDTO addNoteContentReqDTO);
}
