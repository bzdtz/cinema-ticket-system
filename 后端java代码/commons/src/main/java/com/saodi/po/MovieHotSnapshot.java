package com.saodi.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  外部热度榜的一次快照行。它记的是「某一刻从外部源看到的世界」，不是站内可售影片：
 *  movie 表停在 2024 年，所以「最近什么在映、谁最热」只能靠这张表答，
 *  拿 movie.want_number 冒充当下热度会报出一份两年前的榜单。
 * </p>
 *
 * @author saodi
 */
@TableName("movie_hot_snapshot")
public class MovieHotSnapshot implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 一次抓取一个批次号（yyyyMMdd-HHmmss-SSS，到毫秒免得同秒两次刷新撞成一个批次），同一批行共用 */
    private String batchId;

    /** 数据源标识，目前只有 douban */
    private String source;

    /** 榜单名次，1 最热。MySQL 8 里 rank 是保留字，所以列名是 rank_no */
    private Integer rankNo;

    private String title;

    /** 豆瓣评分原样存字符串，空串表示对方没给分 */
    private String rate;

    private String cover;

    /** 源站条目 id（豆瓣 subject id），用来跳转和去重 */
    private String subjectId;

    private String subjectUrl;

    private Date fetchedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getRankNo() {
        return rankNo;
    }

    public void setRankNo(Integer rankNo) {
        this.rankNo = rankNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectUrl() {
        return subjectUrl;
    }

    public void setSubjectUrl(String subjectUrl) {
        this.subjectUrl = subjectUrl;
    }

    public Date getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(Date fetchedAt) {
        this.fetchedAt = fetchedAt;
    }
}
