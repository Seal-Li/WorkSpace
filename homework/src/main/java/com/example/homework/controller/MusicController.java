package com.example.homework.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homework.feign.HomeworkEsFeignClient;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.example.homework.entity.MusicEntity;
import com.example.homework.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 音乐
 *
 * @author seal
 * @date 2023-10-16 17:12:50
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/music" )
@Tag(description = "music" , name = "音乐管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MusicController {

    private final  MusicService musicService;

    @Autowired
    private HomeworkEsFeignClient homeworkEsFeignClient;

    /**
     * 分页查询
     * @param page 分页对象
     * @param music 音乐
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    /**
     * @PreAuthorize("@pms.hasPermission('homework_music_view')" )
     */
    public R getMusicPage(@ParameterObject Page page, @ParameterObject MusicEntity music) {
        LambdaQueryWrapper<MusicEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.ge(Objects.nonNull(music.getSongId()),MusicEntity::getSongId,music.getSongId());
		wrapper.eq(StrUtil.isNotBlank(music.getSongName()),MusicEntity::getSongName,music.getSongName());
		// wrapper.eq(StrUtil.isNotBlank(music.getSongType()),MusicEntity::getSongType,music.getSongType());
		wrapper.like(StrUtil.isNotBlank(music.getSingerName()),MusicEntity::getSingerName,music.getSingerName());
		wrapper.eq(StrUtil.isNotBlank(music.getComposerName()),MusicEntity::getComposerName,music.getComposerName());
		wrapper.eq(StrUtil.isNotBlank(music.getLyricistName()),MusicEntity::getLyricistName,music.getLyricistName());
        wrapper.like(StrUtil.isNotBlank(music.getSongType()), MusicEntity::getSongType, music.getSongType());
        return R.ok(musicService.page(page, wrapper));
    }


    /**
     * 通过id查询音乐
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    /**
     * @PreAuthorize("@pms.hasPermission('homework_music_view')" )
     */
    public R<MusicEntity> getById(@PathVariable("id" ) String id) {
        MusicEntity music = musicService.getById(id);
        return R.ok(music);
    }

    /**
     * 新增音乐
     * @param music 音乐
     * @return R
     */
    @Operation(summary = "新增音乐" , description = "新增音乐" )
    @SysLog("新增音乐" )
    @PostMapping
    /**
     * @PreAuthorize("@pms.hasPermission('homework_music_add')" )
     */
    public R<MusicEntity> save(@RequestBody MusicEntity music) {
        // 需要先插入MySQL，因为要获取主键
        boolean save = musicService.save(music);

        // 再插入ES
        homeworkEsFeignClient.save(music);

        // 返回数据
        return R.ok(music);
    }

    /**
     * 修改音乐
     * @param music 音乐
     * @return R
     */
    @Operation(summary = "修改音乐" , description = "修改音乐" )
    @SysLog("修改音乐" )
    @PutMapping
    /**
     * @PreAuthorize("@pms.hasPermission('homework_music_edit')" )
     */
    public R<MusicEntity> updateById(@RequestBody MusicEntity music) {
        // 修改MySQL
        boolean b = musicService.updateById(music);

        // 修改ES
        homeworkEsFeignClient.updateById(music);

        // 返回结果
        return R.ok(music);
    }

    /**
     * 通过id删除音乐
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除音乐" , description = "通过id删除音乐" )
    @SysLog("通过id删除音乐" )
    @DeleteMapping
    /**
     * @PreAuthorize("@pms.hasPermission('homework_music_del')" )
     */
    public R<Boolean> removeById(@RequestBody Long[] ids) {
        // 删除MySQL中的数据
        boolean b = musicService.removeBatchByIds(CollUtil.toList(ids));

        // 删除ES中的数据
        homeworkEsFeignClient.removeById(ids);

        // 返回结果
        return R.ok(b);
    }


    /**
     * 导出excel 表格
     * @param music 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    /**
     * @PreAuthorize("@pms.hasPermission('homework_music_export')" )
     */
    public List<MusicEntity> export(MusicEntity music,Integer[] ids) {
        return musicService.list(Wrappers.lambdaQuery(music).in(ArrayUtil.isNotEmpty(ids), MusicEntity::getId, ids));
    }
}