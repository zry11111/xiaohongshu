package com.zry.xiaohongshu.search.domain.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
public interface SelectMapper {
    List<Map<String, Object>> selectEsNoteIndexData(@Param("noteId") long noteId);
}
