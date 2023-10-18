package com.example.homework.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 音乐
 *
 * @author seal
 * @date 2023-10-16 17:12:50
 */
@Data
@TableName("t_music")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "音乐")
public class MusicEntity extends Model<MusicEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.AUTO)
    @Schema(description="主键")
    private Long id;

	/**
	* 歌曲id
	*/
    @Schema(description="歌曲id")
    private String songId;

	/**
	* 歌曲名称
	*/
    @Schema(description="歌曲名称")
    private String songName;

	/**
	* 歌曲类型
	*/
    @Schema(description="歌曲类型")
    private String songType;

	/**
	* 歌手
	*/
    @Schema(description="歌手")
    private String singerName;

	/**
	* 作曲
	*/
    @Schema(description="作曲")
    private String composerName;

	/**
	* 作词
	*/
    @Schema(description="作词")
    private String lyricistName;

	/**
	* 歌词
	*/
    @Schema(description="歌词")
    private String lyrics;

	/**
	* 发布日期
	*/
    @Schema(description="发布日期")
    private String releaseDate;

	/**
	* 热门评论
	*/
    @Schema(description="热门评论")
    private String hotComment;

	/**
	* 最新评论
	*/
    @Schema(description="最新评论")
    private String latestComment;

	/**
	* 喜欢数量
	*/
    @Schema(description="喜欢数量")
    private String likeCount;

	/**
	* 评论数量
	*/
    @Schema(description="评论数量")
    private String commentCount;
}