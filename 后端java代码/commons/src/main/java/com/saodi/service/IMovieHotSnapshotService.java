package com.saodi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saodi.po.MovieHotSnapshot;

import java.util.List;

/**
 * <p>
 *  服务类。读侧只有一个口径：最近一次成功抓取的那一批。
 * </p>
 *
 * @author saodi
 */
public interface IMovieHotSnapshotService extends IService<MovieHotSnapshot> {

    /**
     * 最近一批榜单，按名次升序；一次都没抓成功过就是空列表。
     * 空列表不是「今天没人看」，是「外部源这条没通」，调用方得把这两件事分开说。
     */
    List<MovieHotSnapshot> latestBatch();
}
