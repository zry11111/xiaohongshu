package com.zry.xiaohongshu.oss.biz.service;

import com.zry.framework.common.reponse.Response;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Response<?> uploadFile(MultipartFile file);
}
