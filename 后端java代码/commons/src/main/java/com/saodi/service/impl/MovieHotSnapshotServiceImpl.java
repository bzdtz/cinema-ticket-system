package com.saodi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saodi.mapper.MovieHotSnapshotMapper;
import com.saodi.po.MovieHotSnapshot;
import com.saodi.service.IMovieHotSnapshotService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author saodi
 */
@Service
public class MovieHotSnapshotServiceImpl extends ServiceImpl<MovieHotSnapshotMapper, MovieHotSnapshot>
        implements IMovieHotSnapshotService {

    @Override
    public List<MovieHotSnapshot> latestBatch() {
        // 批次靠自增 id 定位，不靠 fetched_at：同一批是同一秒写进去的，时间戳会并列，
        // 而 id 最大那一行必然属于最后一次插入的批次。
        QueryWrapper<MovieHotSnapshot> probe = new QueryWrapper<>();
        probe.orderByDesc("id").last("LIMIT 1");
        MovieHotSnapshot newest = getOne(probe, false);
        if (newest == null || newest.getBatchId() == null) {
            return Collections.emptyList();
        }
        QueryWrapper<MovieHotSnapshot> rows = new QueryWrapper<>();
        rows.eq("batch_id", newest.getBatchId()).orderByAsc("rank_no");
        return list(rows);
    }
}
