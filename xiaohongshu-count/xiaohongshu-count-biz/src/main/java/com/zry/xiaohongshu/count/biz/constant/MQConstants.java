package com.zry.xiaohongshu.count.biz.constant;

public interface MQConstants {
    String TOPIC_COUNT_FOLLOWING = "CountFollowingTopic";
    String TOPIC_COUNT_FANS = "CountFansTopic";
    /**
     * Topic: 粉丝数计数入库
     */
    String TOPIC_COUNT_FANS_2_DB = "CountFans2DBTopic";
}
