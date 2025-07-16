package com.zry.xiaohongshu.oss.biz.controller;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.oss.biz.service.impl.FileServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileServiceImpl fileService;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return fileService.uploadFile(file);
    }
}
