package com.example.homework.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.homework.entity.MusicEntity;
import com.example.homework.mapper.MusicMapper;
import com.example.homework.service.MusicService;
import org.springframework.stereotype.Service;

/**
 * 音乐
 *
 * @author seal
 * @date 2023-10-16 17:12:50
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, MusicEntity> implements MusicService {
    @Override
    public IPage<MusicEntity> localPage(Page page, MusicEntity music) {
        LambdaQueryWrapper<MusicEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MusicEntity::getId,music.getId());
        wrapper.eq(StrUtil.isNotBlank(music.getSongId()),MusicEntity::getSongId,music.getSongId());
        wrapper.eq(StrUtil.isNotBlank(music.getSongName()),MusicEntity::getSongName,music.getSongName());
        wrapper.eq(StrUtil.isNotBlank(music.getSingerName()),MusicEntity::getSingerName,music.getSingerName());
        wrapper.eq(StrUtil.isNotBlank(music.getSongType()),MusicEntity::getSongType,music.getSongType());
        wrapper.eq(StrUtil.isNotBlank(music.getComposerName()),MusicEntity::getComposerName,music.getComposerName());
        wrapper.eq(StrUtil.isNotBlank(music.getLyricistName()),MusicEntity::getLyricistName,music.getLyricistName());
        wrapper.eq(StrUtil.isNotBlank(music.getLyrics()),MusicEntity::getLyrics,music.getLyrics());
        wrapper.eq(StrUtil.isNotBlank(music.getHotComment()),MusicEntity::getHotComment,music.getHotComment());
        wrapper.eq(StrUtil.isNotBlank(music.getLatestComment()),MusicEntity::getLatestComment,music.getLatestComment());
        wrapper.eq(StrUtil.isNotBlank(music.getReleaseDate()),MusicEntity::getReleaseDate,music.getReleaseDate());
        wrapper.eq(StrUtil.isNotBlank(music.getCommentCount()),MusicEntity::getCommentCount,music.getCommentCount());
        wrapper.eq(StrUtil.isNotBlank(music.getLikeCount()),MusicEntity::getLikeCount,music.getLikeCount());
        return page(page, wrapper);
    }
}