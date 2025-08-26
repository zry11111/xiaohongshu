package com.zry.xiaohongshu.user.biz.rpc;

import com.zry.xiaohongshu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component

public class DistributedIdGeneratorRpcService {
    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;
    private static final String BIZ_TAG_XIAOHONGSHU_ID = "leaf-segment-xiaohongshu-id";
    private static final String BIZ_TAG_USER_ID = "leaf-segment-user-id";
    public String generateId(){
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_XIAOHONGSHU_ID);
    }
    public String getUserId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_USER_ID);
    }
}
