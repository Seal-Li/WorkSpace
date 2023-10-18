package com.example.homework.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.homework.entity.MusicEntity;

public interface MusicService extends IService<MusicEntity> {
    IPage<MusicEntity> localPage(Page page, MusicEntity music);

}