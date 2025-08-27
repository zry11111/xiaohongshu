package com.zry.xiaohongshu.note.biz.rpc;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.api.UserFeignApi;
import com.zry.xiaohongshu.user.dto.req.FindUserByIdReqDTO;
import com.zry.xiaohongshu.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserRpcService {
    @Resource
    private UserFeignApi userFeignApi;
    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    public FindUserByIdRspDTO findById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);

        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }
}
