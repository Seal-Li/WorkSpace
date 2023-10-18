package com.tsingtao.homework.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsingtao.homework.entity.Music;
import com.tsingtao.homework.mapper.MusicMapper;
import com.tsingtao.homework.service.MusicService;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公司管理
 *
 * @author seal
 * @date 2023-10-12 23:44:05
 */
@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public IPage<Music> localPage(Page<Music> page, Music music) {
        LambdaEsQueryWrapper<Music> wrapper = new LambdaEsQueryWrapper<>();
        // 构造条件
        wrapper.eq(Music::getId, music.getId());
        wrapper.eq(Music::getSongId, music.getSongId());
        wrapper.eq(Music::getSongName, music.getSongName());
        wrapper.eq(Music::getSongType, music.getSongType());
        wrapper.eq(Music::getSingerName, music.getSingerName());
        wrapper.eq(Music::getComposerName, music.getComposerName());
        wrapper.eq(Music::getLyricistName, music.getLyricistName());
        wrapper.eq(Music::getReleaseDate, music.getReleaseDate());
        wrapper.eq(Music::getLyrics, music.getLyrics());
        wrapper.eq(Music::getHotComment, music.getHotComment());
        wrapper.eq(Music::getLatestComment, music.getLatestComment());
        wrapper.eq(Music::getCommentCount, music.getCommentCount());
        wrapper.eq(Music::getLikeCount, music.getLikeCount());

        // 当前页
        int current = (int) page.getCurrent();
        // 每页大小
        int size = (int) page.getSize();

        // 查询
        EsPageInfo<Music> documentEsPageInfo = musicMapper.pageQuery(wrapper, current, size);

        // 转化：将ES的分页转化为Pig的分页
        Page<Music> result = new Page<>(documentEsPageInfo.getPageNum(), documentEsPageInfo.getPageSize());

        // 设置查询数据
        result.setRecords(documentEsPageInfo.getList());

        // 返回结果
        return result;
    }

    @Override
    public Music localSave(Music music) {
        musicMapper.insert(music);
        return music;
    }

    @Override
    public Music localUpdateById(Music music) {
        musicMapper.updateById(music);
        return music;
    }

    @Override
    public List<String> localRemoveBatchByIds(String[] ids) {
        ArrayList<String> idList = CollUtil.toList(ids);
        musicMapper.deleteBatchIds(idList);
        return idList;
    }

    /**
    public List<String> localRemoveBatchByIds(List<Music> musicList) {
        List<String> idList = musicList.stream()
              .peek(item -> item.setId(item.getSong_id()))
              .map(Music::getId)
              .collect(Collectors.toList());
        musicMapper.deleteBatchIds(idList);
        return idList;
    }
    */

}