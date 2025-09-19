package com.zry.xiaohongshu.note.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.note.biz.model.vo.FindTopicListReqVO;
import com.zry.xiaohongshu.note.biz.model.vo.FindTopicRspVO;

import java.util.List;

public interface TopicService {
    Response<List<FindTopicRspVO>> findTopicList(FindTopicListReqVO findTopicListReqVO);
}
