package com.zry.xiaohongshu.kv.biz;

import com.zry.xiaohongshu.kv.biz.domain.dataobject.NoteContentDO;
import com.zry.xiaohongshu.kv.biz.domain.repository.NoteContentRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Slf4j
public class CassandraTests {
    @Resource
    private NoteContentRepository noteContentRepository;
    @Test
    void insert(){
        NoteContentDO noteContentDO = NoteContentDO.builder()
                .id(UUID.randomUUID())
                .content("测试笔记内容插入")
                .build();
        noteContentRepository.save(noteContentDO);
    }
}
