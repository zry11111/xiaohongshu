package com.zry.xiaohongshu.user.biz.rpc;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.count.api.CountFeignApi;
import com.zry.xiaohongshu.count.dto.FindUserCountsByIdReqDTO;
import com.zry.xiaohongshu.count.dto.FindUserCountsByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CountRpcService {
    @Resource
    private CountFeignApi countFeignApi;
    public FindUserCountsByIdRspDTO findUserCountById(Long userId) {
        FindUserCountsByIdReqDTO findUserCountsByIdReqDTO = new FindUserCountsByIdReqDTO();
        findUserCountsByIdReqDTO.setUserId(userId);

        Response<FindUserCountsByIdRspDTO> response = countFeignApi.findUserCount(findUserCountsByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

}
