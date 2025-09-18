package com.zry.xiaohongshu.note.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPublishedNoteListRspVO {

    private List<NoteItemRspVO> notes;
    // 将每次查询的最后一个笔记 ID 作为下一次查询的游标
    private Long nextCursor;

}