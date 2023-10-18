package com.tsingtao.homework.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.tsingtao.homework.entity.Music;
import com.tsingtao.homework.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司管理
 *
 * @author seal
 * @since 2023-10-12 23:44:05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
@Tag(description = "music", name = "音乐管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MusicController {

    private final MusicService musicService;

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param music   音乐管理
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public R<IPage<Music>> getMusicPage(@ParameterObject Page<Music> page, @ParameterObject Music music) {
        return R.ok(musicService.localPage(page, music));
    }

    /**
     * 新增音乐管理
     *
     * @param music 音乐管理
     * @return R
     */
    @Operation(summary = "新增音乐管理", description = "新增音乐管理")
    @SysLog("新增音乐管理")
    @PostMapping
    public R<Music> save(@RequestBody Music music) {
        return R.ok(musicService.localSave(music));
    }

    /**
     * 修改音乐管理
     *
     * @param music 音乐管理
     * @return R
     */
    @Operation(summary = "修改音乐管理", description = "修改音乐管理")
    @SysLog("修改音乐管理")
    @PutMapping
    public R<Music> updateById(@RequestBody Music music) {
        return R.ok(musicService.localUpdateById(music));
    }

    /**
     * 通过id删除音乐管理
     *
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除音乐管理", description = "通过id删除音乐管理")
    @SysLog("通过id删除音乐管理")
    @DeleteMapping
    public R<List<String>> removeById(@RequestBody String[] ids) {
        return R.ok(musicService.localRemoveBatchByIds(ids));
    }
}