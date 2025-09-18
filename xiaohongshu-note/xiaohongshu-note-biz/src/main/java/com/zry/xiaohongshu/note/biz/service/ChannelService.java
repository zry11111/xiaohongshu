package com.zry.xiaohongshu.note.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.note.biz.model.vo.FindChannelRspVO;

import java.util.List;

public interface ChannelService {
    Response<List<FindChannelRspVO>> findChannelList();
}
