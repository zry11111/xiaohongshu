package com.zry.xiaohongshu.oss.biz.service.impl;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.oss.biz.service.FileService;
import com.zry.xiaohongshu.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private static final String BUCKET_NAME = "xiaohongshu";
    @Resource
    private FileStrategy fileStrategy;
    @Override
    public Response<?> uploadFile(MultipartFile file) {

        String url = fileStrategy.uploadFile(file, BUCKET_NAME);

        return Response.success(url);
    }
}
