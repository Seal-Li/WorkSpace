package com.tsingtao.homework.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsingtao.homework.entity.Music;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

public interface MusicService {


    IPage<Music> localPage(Page<Music> page, Music music);

    Music localSave(Music music);

    Music localUpdateById(Music music);

    List<String> localRemoveBatchByIds(String[] ids);

    // List<String> localRemoveBatchByIds(List<Music> musicList);

}