package com.zry.xiaohongshu.note.biz.domain.mapper;

import com.zry.xiaohongshu.note.biz.domain.dataobject.TopicDO;

import java.util.List;

public interface TopicDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicDO record);

    int insertSelective(TopicDO record);

    TopicDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicDO record);

    int updateByPrimaryKey(TopicDO record);
    String selectNameByPrimaryKey(Long id);
    List<TopicDO> selectByLikeName(String keyword);

    List<TopicDO> selectByTopicIdIn(List<Long> topicIds);

    TopicDO selectByTopicName(String topicName);

    void batchInsert(List<TopicDO> newTopics);
}