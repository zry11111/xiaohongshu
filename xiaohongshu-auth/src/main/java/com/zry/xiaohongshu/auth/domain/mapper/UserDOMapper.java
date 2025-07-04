package com.zry.xiaohongshu.auth.domain.mapper;

import com.zry.xiaohongshu.auth.domain.dataobject.UserDO;

public interface UserDOMapper {
    UserDO selectByPhone(String phone);
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}