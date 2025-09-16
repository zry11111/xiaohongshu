package com.zry.xiaohongshu.comment.biz.domain.mapper;

import com.zry.xiaohongshu.comment.biz.domain.dataobject.CommentDO;
import com.zry.xiaohongshu.comment.biz.model.bo.CommentBO;
import com.zry.xiaohongshu.comment.biz.model.bo.CommentHeatBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommentDO record);

    int insertSelective(CommentDO record);

    CommentDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommentDO record);

    int updateByPrimaryKey(CommentDO record);

    List<CommentDO> selectByCommentIds(@Param("commentIds") List<Long> commentIds);
    int batchInsert(@Param("comments") List<CommentBO> comments);
    int batchUpdateHeatByCommentIds(@Param("commentIds") List<Long> commentIds,
                                    @Param("commentHeatBOS") List<CommentHeatBO> commentHeatBOS);
    CommentDO selectEarliestByParentId(Long parentId);
    int updateFirstReplyCommentIdByPrimaryKey(@Param("firstReplyCommentId") Long firstReplyCommentId,
                                              @Param("id") Long id);
    List<CommentDO> selectPageList(@Param("noteId") Long noteId,
                                   @Param("offset") long offset,
                                   @Param("pageSize") long pageSize);
    List<CommentDO> selectTwoLevelCommentByIds(@Param("commentIds") List<Long> commentIds);
    /**
     * 查询热门评论
     * @param noteId
     * @return
     */
    List<CommentDO> selectHeatComments(Long noteId);
    Long selectChildCommentTotalById(Long commentId);
    List<CommentDO> selectChildPageList(@Param("parentId") Long parentId,
                                        @Param("offset") long offset,
                                        @Param("pageSize") long pageSize);
    List<CommentDO> selectCommentCountByIds(@Param("commentIds") List<Long> commentIds);
    List<CommentDO> selectChildCommentsByParentIdAndLimit(@Param("parentId") Long parentId,
                                                          @Param("limit") int limit);
}