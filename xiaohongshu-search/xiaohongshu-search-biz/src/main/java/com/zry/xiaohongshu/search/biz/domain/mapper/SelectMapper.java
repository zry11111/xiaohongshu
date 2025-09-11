package com.zry.xiaohongshu.search.biz.domain.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
public interface SelectMapper {
    List<Map<String, Object>> selectEsNoteIndexData(@Param("noteId") Long noteId, @Param("userId") Long userId);
    /**
     * 查询用户索引所需的全字段数据
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectEsUserIndexData(@Param("userId") Long userId);
}
