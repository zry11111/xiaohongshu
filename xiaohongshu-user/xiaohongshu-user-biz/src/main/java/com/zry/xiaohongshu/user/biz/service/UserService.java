package com.zry.xiaohongshu.user.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.biz.model.vo.FindUserProfileReqVO;
import com.zry.xiaohongshu.user.biz.model.vo.FindUserProfileRspVO;
import com.zry.xiaohongshu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.zry.xiaohongshu.user.dto.req.*;
import com.zry.xiaohongshu.user.dto.resp.FindUserByIdRspDTO;
import com.zry.xiaohongshu.user.dto.resp.FindUserByPhoneRspDTO;

import java.util.List;

public interface UserService {
    Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO);

    Response<Long> register(RegisterUserReqDTO registerUserReqDTO);
    Response<FindUserByPhoneRspDTO> findByPhone(FindUserByPhoneReqDTO findUserByPhoneReqDTO);
    Response<?> updatePassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO);
    Response<FindUserByIdRspDTO> findById(FindUserByIdReqDTO findUserByIdReqDTO);
    Response<List<FindUserByIdRspDTO>> findByIds(FindUsersByIdsReqDTO findUsersByIdsReqDTO);
    Response<FindUserProfileRspVO> findUserProfile(FindUserProfileReqVO findUserProfileReqVO);
}
