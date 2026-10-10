package com.saodi.config;

import com.saodi.service.HotMovieSyncService;
import com.saodi.service.IMovieHotSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  第一次启动时抓一版热度榜，把空表填上。
 *
 *  只做「表是空的」这一种情况：已有批次时不碰外部接口，免得每次重启都在向人家要一份榜单，
 *  也免得重启把「刚刚那份」换成别的时间点。要新的那份请点首页的刷新。
 * </p>
 *
 * @author saodi
 */
@Component
public class HotSnapshotBootstrap implements ApplicationRunner {

    @Autowired
    private IMovieHotSnapshotService snapshots;
    @Autowired
    private HotMovieSyncService syncService;

    @Override
    public void run(ApplicationArguments args) {
        if (!snapshots.latestBatch().isEmpty()) {
            System.out.println("--------热度榜快照已有 " + snapshots.latestBatch().size() + " 条，不重复抓取--------");
            return;
        }
        try {
            System.out.println("--------热度榜快照是空的，抓第一版：" + syncService.refresh(20) + "--------");
        } catch (Exception e) {
            // 抓不到不拦启动：热映区会显示「外部源这次没通」，站内自己的影片和场次照常用
            System.out.println("--------热度榜第一版没抓到（" + e.getClass().getSimpleName()
                    + "），热映区会走空态，不影响其它功能--------");
        }
    }
}
