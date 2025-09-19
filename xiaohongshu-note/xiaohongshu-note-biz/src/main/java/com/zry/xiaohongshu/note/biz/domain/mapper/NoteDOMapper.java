package com.zry.xiaohongshu.note.biz.domain.mapper;

import com.zry.xiaohongshu.note.biz.domain.dataobject.NoteDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteDO record);

    int insertSelective(NoteDO record);

    NoteDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteDO record);

    int updateByPrimaryKey(NoteDO record);
    int updateVisibleOnlyMe(NoteDO noteDO);
    int updateIsTop(NoteDO noteDO);
    int selectCountByNoteId(Long noteId);

    Long selectCreatorIdByNoteId(Long noteId);

    List<NoteDO> selectPublishedNoteListByUserIdAndCursor(@Param("creatorId") Long creatorId,
                                                          @Param("cursor") Long cursor);

    int selectTotalCount(Long channelId);

    List<NoteDO> selectPageList(@Param("channelId") Long channelId,
                                @Param("offset") long offset,
                                @Param("pageSize") long pageSize);
    List<NoteDO> selectPageListByCreatorId(@Param("creatorId") Long creatorId,
                                           @Param("offset") long offset,
                                           @Param("pageSize") long pageSize);

    int selectTotalCountByCreatorId(Long creatorId);

    List<NoteDO> selectByNoteIds(@Param("noteIds") List<Long> noteIds);
}