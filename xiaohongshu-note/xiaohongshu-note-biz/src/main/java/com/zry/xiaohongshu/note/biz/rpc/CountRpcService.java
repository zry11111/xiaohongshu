package com.zry.xiaohongshu.note.biz.rpc;

import cn.hutool.core.collection.CollUtil;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.count.api.CountFeignApi;
import com.zry.xiaohongshu.count.dto.FindNoteCountByIdReqDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountByIdRspDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdRspDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdsReqDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CountRpcService {
    @Resource
    private CountFeignApi countFeignApi;

    public List<FindNoteCountsByIdRspDTO> findByNoteIds(List<Long> noteIds) {
        FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO = new FindNoteCountsByIdsReqDTO();
        findNoteCountsByIdsReqDTO.setNoteIds(noteIds);

        Response<List<FindNoteCountsByIdRspDTO>> response = countFeignApi.findNotesCount(findNoteCountsByIdsReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData()) || CollUtil.isEmpty(response.getData())) {
            return null;
        }

        return response.getData();
    }

    public FindNoteCountByIdRspDTO findNoteCountById(Long noteId) {
        FindNoteCountByIdReqDTO findNoteCountByIdReqDTO = new FindNoteCountByIdReqDTO();
        findNoteCountByIdReqDTO.setNoteId(noteId);

        Response<FindNoteCountByIdRspDTO> response = countFeignApi.findNoteCount(findNoteCountByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }
}
