package com.tsingtao.homework.entity;

import lombok.*;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.IdType;

import java.time.LocalDateTime;

/**
 * 音乐管理
 *
 * @author seal
 * @date 2023-10-12 23:44:05
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@IndexName(value = "music")
public class Music {

	/**
	* 音乐ID
	 * MySQL：Long id
	 * ES：String id
	*/
	@IndexId(type = IdType.CUSTOMIZE)
    private String id;

	/**
	* 音乐id
	*/
	// @IndexField
    @IndexId(type = IdType.CUSTOMIZE)
    private String songId;

    /**
     * 歌曲名称
     */
    @IndexField
    private String songName;

    /**
     * 歌曲类型
     */
    @IndexField
    private String songType;

    /**
     * 歌手名称
     */
    @IndexField
    private String singerName;

    /**
     * 作曲名称
     */
    @IndexField
    private String composerName;

    /**
     * 作词名称
     */
    @IndexField
    private String lyricistName;

    /**
     * 歌词
     */
    @IndexField
    private String lyrics;

    /**
     * 发布时间
     */
    @IndexField
    private String releaseDate;

    /**
     * 热门评论
     */
    @IndexField
    private String hotComment;

    /**
     * 最新评论
     */
    @IndexField
    private String latestComment;

    /**
     * 喜欢数量
     */
    @IndexField
    private String likeCount;

    /**
     * 评论数量
     */
    @IndexField
    private String commentCount;
}