package com.zry.xiaohongshu.user.relation.api;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.relation.constant.ApiConstants;
import com.zry.xiaohongshu.user.relation.dto.req.FollowUserReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface UserRelationFeignApi {
    String PREFIX = "/relation";
    @PostMapping(value = PREFIX + "/isFollowed")
    Response<Boolean> isFollowed(@RequestBody FollowUserReqDTO followUserReqDTO);
}
