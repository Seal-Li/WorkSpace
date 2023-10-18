package com.example.homework.feign;

import com.pig4cloud.pig.common.core.util.R;
import com.example.homework.entity.MusicEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author seal
 */
@FeignClient(contextId = "homeworkEsFeignClient", value = "homework-es")
public interface HomeworkEsFeignClient {


    @PostMapping("/music")
    R<MusicEntity> save(@RequestBody MusicEntity music);

    @PutMapping("/music")
    R<MusicEntity> updateById(@RequestBody MusicEntity music);

    @DeleteMapping("/music")
    R<List<Long>> removeById(@RequestBody Long[] ids);

}
