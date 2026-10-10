<template>
    <div class="content">

        <div class="left">
            <div class="hot">
                <div class="movie">
                    <div style="display: flex;justify-content: space-between;">
                        <h2 style="margin: 0;">正在热映</h2>
                        <p style="line-height: 32px;">全部 ></p>
                    </div>
                    <div style="display: flex;flex-direction: row; justify-content: space-between; flex-wrap: wrap;">
                        <div class="movie-item" v-for="movie in movies">
                            <div class="movie-poster" :style="{ backgroundImage: 'url(' + movie.banner + ')' }"
                                @click="tocinemas(movie)">
                                <!-- 从外部榜单补进来的片子没有 tag_movie 关系，tag 是 null：
                                     原来这里直接写 movie.tag.tag，一条 null 就把整个热映板块渲染崩掉。 -->
                                <div v-if="movie.tag">
                                    <img :src="movie.tag.tag" style="position: relative;top: 10px;left: -3px;">
                                </div>
                                <div class="movie-info">
                                    <p>{{ movie.name }}</p>
                                    <p>{{ movie.score == null ? '暂无评分' : movie.score }}</p>
                                </div>

                            </div>
                            <div class="movie-sale">
                                <p style="height: 40px;line-height: 40px;text-align: center;" @click="tocinemas(movie)">
                                    购票
                                </p>
                            </div>
                        </div>
                    </div>


                </div>
            </div>

            <div class="about-to">
                <div class="movie">
                    <div style="display: flex;justify-content: space-between;">
                        <h2 style="margin: 0;">即将上映</h2>
                        <p style="line-height: 32px;">全部 ></p>
                    </div>
                    <div style="display: flex;flex-direction: row; justify-content: space-between; flex-wrap: wrap;">
                        <div class="movie-item" v-for="movie in will" style="margin-bottom: 30px;">
                            <div class="movie-poster" :style="{ backgroundImage: 'url(' + movie.banner + ')' }">
                                <div v-if="movie.tag">
                                    <img :src="movie.tag.tag" style="position: relative;top: 10px;left: -3px;">
                                </div>
                                <div class="movie-info">
                                    <p>{{ movie.name }}</p>

                                </div>

                            </div>
                            <div style="display: flex;justify-content: space-around;height: 40px;">
                                <div style="width: 50%;border-right:1px solid #efefef;">
                                    <p style="height: 40px;line-height: 40px;text-align: center;">
                                        预告片
                                    </p>
                                </div>
                                <div style="width: 50%;">
                                    <p style="height: 40px;line-height: 40px;text-align: center;">
                                        预售
                                    </p>
                                </div>
                            </div>
                            <div style="display: flex;justify-content: space-around;color:#999999;margin: 10px 0;">
                                {{ showtime(movie.releaseTime) }}上映

                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="list">

            </div>
        </div>
        <div class="right">
            <div class="box-office">
                <div class="movie">
                    <div style="display: flex;justify-content: space-between;">
                        <h2 style="margin: 0;">外部热度榜</h2>
                        <p style="line-height: 32px;font-size: 12px;color: #999;">{{ hotTimeText }}</p>
                    </div>
                    <!-- 这块原来是 data() 里硬写的「前10票房」：那串数是 2023 年贴进源码的演示值，
                         页面上看着像实时票房，其实是死的。现在读库里的榜单快照，
                         抓取时间跟着标题走——热度不给时间就没法判断它是哪天的事。 -->
                    <div v-for="row in hotRows">
                        <div class="frist-office" v-if="row.rank == 1" @click="toHotMovie(row)">
                            <div class="frist-office-img"
                                :style="{ backgroundImage: 'url(' + row.cover + ')', backgroundSize: '120px 100px' }">
                            </div>
                            <div class="frist-office-info">
                                <p style="font-size: 22px;">{{ row.title }}</p>
                                <p style="color: red;">{{ row.rate || '暂无评分' }}</p>
                            </div>

                        </div>
                        <div class="other-office" v-else @click="toHotMovie(row)">
                            <div style="width: 65%;">
                                <i :style="{ color: (row.rank == 2 || row.rank == 3 ? 'red' : '#999') }"
                                    style="line-height: 35px;font-size: 20px;width: 30px; margin-right: 10px;">{{ row.rank
                                    }}</i>
                                <span style="width: 150px;font-size: 16px;">{{ row.title }}</span>
                            </div>
                            <div style="display:flex ;">
                                <span style="font-size: 12px;color:#999;">{{ row.inLibrary ? '站内有' : '站内暂无' }}</span>
                                <div style="width: 55px;text-align: center;">
                                    <i style="font-size: 15px;line-height: 35px;color: red;">{{ row.rate }}</i>

                                </div>
                            </div>

                        </div>
                    </div>
                    <p v-if="!hotRows.length" style="color: #999;margin: 20px 0;font-size: 13px;">{{ hotEmptyText }}</p>
                    <div class="hot-actions">
                        <p :class="{ dim: hotBusy }" @click="refreshHot">{{ hotBusy ? '抓取中…' : '刷新榜单' }}</p>
                        <p :class="{ dim: hotBusy }" @click="promoteHot">补进站内前 5 部</p>
                    </div>
                    <p class="hot-result" v-if="hotResult">{{ hotResult }}</p>
                </div>
            </div>
            <div class="want">

            </div>
            <div class="top100">
                <div>
                    <div class="movie">
                        <h2 style="margin: 0;">站内评分榜</h2>
                    </div>
                    <div v-for="i, index in scoreList">
                        <div class="frist-office" v-if="index == 0">
                            <div class="frist-office-img" :style="{ backgroundImage: 'url(' + i.banner + ')' }">
                            </div>
                            <div class="frist-office-info">
                                <p style="font-size: 22px;">{{ i.name }}</p>
                                <p style="color: red;">{{ i.score }}分</p>
                            </div>

                        </div>
                        <div class="other-office" v-else>
                            <i style="font-size: 20px;line-height: 35px;">{{ index + 1 }}</i>
                            <span style="width: 65%;font-size: 18px;">{{ i.name }}</span>
                            <span style="font-size: 14px;color: red;">{{ i.score }}分</span>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>
</template>

<script>
import moment from "moment";
export default {
    data() {
        return {
            movies: [],
            will:
                [],
            // 外部热度榜读的是库里的快照（movie_hot_snapshot 最近一批），不是每次打开首页去敲豆瓣：
            // 那个接口是非官方的，挂了只能把这块显示成「没抓到」，不能把首页一起拖慢。
            hot: { available: false, source: '', fetchedAt: '', count: 0, rows: [] },
            hotBusy: false,
            // 刷新/补片的结果只在点过之后显示一行，不弹窗：这一页上还有一堆别的东西，弹层会挡住。
            hotResult: ''
        }
    },

    methods: {
        tocinemas(row) {
            console.log(row);
            this.$router.push({ path: '/movie', query: { id: row.id } });

        },
        getHotMovie() {
            this.$axios({
                method: 'get',
                url: '/app/movie/index/hot/8'
            }).then((res) => {
                // console.log("输出热播的电影");
                console.log(res.data);
                this.movies = res.data.data;
            }).catch((err) => {

            });
        },
        getWantMovie() {
            this.$axios({
                method: 'get',
                url: '/app/movie/index/want/8'
            }).then((res) => {
                console.log("输出即将上映的电影");
                console.log(res.data);
                this.will = res.data.data;
            }).catch((err) => {

            });
        },
        // ---- 外部热度榜：读快照、刷快照、把榜单补成站内影片 ----
        // 右侧原来那两块（写死的「前10票房」数组、只有一个条目的 TOP100）一并去掉了：
        // 页面上一眼能看见的地方不该摆一份不会变的数。
        getHot() {
            return this.$axios({
                method: 'get',
                url: '/app/hot/list'
            }).then((res) => {
                this.hot = res.data.data || this.hot;
            }).catch((err) => {
                this.hotResult = '热度榜没读到：' + this.why(err);
            });
        },
        // 刷新只写快照表，一行都不碰 movie/showtimes；抓失败时后端把旧批次原样留着。
        refreshHot() {
            if (this.hotBusy) {
                return;
            }
            this.hotBusy = true;
            this.hotResult = '';
            this.$axios({
                method: 'post',
                url: '/app/hot/refresh',
                params: { limit: 20 }
            }).then((res) => {
                var data = res.data.data || {};
                if (!data.ok) {
                    this.hotResult = '外部源这次没通：' + data.reason + '（旧榜 ' + data.kept + ' 部还在）';
                    return;
                }
                this.hotResult = '抓到 ' + data.count + ' 部，批次 ' + data.batchId;
                return this.getHot();
            }).catch((err) => {
                this.hotResult = '没刷成：' + this.why(err);
            }).finally(() => {
                this.hotBusy = false;
            });
        },
        // 这一步会动 movie 和 showtimes 两张业务表，所以单独一个按钮、单独一句说明，
        // 不跟着刷新自动跑：榜单天天变，排片表是要卖票的。
        promoteHot() {
            if (this.hotBusy) {
                return;
            }
            this.hotBusy = true;
            this.hotResult = '';
            this.$axios({
                method: 'post',
                url: '/app/hot/promote',
                params: { limit: 5 }
            }).then((res) => {
                var data = res.data.data || {};
                if (!data.ok) {
                    this.hotResult = data.reason;
                    return;
                }
                var already = data.alreadyThere || [];
                this.hotResult = '补进 ' + (data.added || []).length + ' 部、排出 ' + data.showtimesCreated + ' 场'
                    + (already.length ? '；' + already.join('、') + ' 库里已经有了' : '');
                this.getHot();
                this.getHotMovie();
            }).catch((err) => {
                this.hotResult = '没补成：' + this.why(err);
            }).finally(() => {
                this.hotBusy = false;
            });
        },
        // 榜单上的片子不等于本站有票：没进库的那几部点开来只说一句为什么点不动。
        toHotMovie(row) {
            if (!row.movieId) {
                this.hotResult = '《' + row.title + '》站内还没有这部片，「补进站内前 5 部」点下去才会有场次。';
                return;
            }
            this.$router.push({ path: '/movie', query: { id: row.movieId } });
        },
        why(err) {
            if (!err) {
                return '请求没通';
            }
            return typeof err === 'string' ? err : (err.msg || err.message || '请求没通');
        }
    },
    mounted() {
        this.getHotMovie();
        this.getWantMovie();
        this.getHot();
    },
    computed: {
        hotTimeText() {
            if (!this.hot.fetchedAt) {
                return '还没有快照';
            }
            var source = this.hot.source === 'douban' ? '豆瓣' : this.hot.source;
            return source + ' ' + this.hot.fetchedAt + ' 抓的';
        },
        hotRows() {
            return (this.hot.rows || []).slice(0, 10);
        },
        hotEmptyText() {
            return '库里还没有榜单快照。点下面的「刷新榜单」抓一次（这块要登录态）。';
        },
        // 站内评分榜：直接用热映那 8 部排出来的，不再摆一份写死的 TOP100。
        scoreList() {
            return (this.movies || []).filter(function (m) {
                return m.score != null;
            }).slice().sort(function (a, b) {
                return b.score - a.score;
            });
        },
        showtime: () => {

            return (time) => {

                return moment((new Date(time))).format('M月DD日')
            }
        }
    }


}
</script>

<style>
.content {
    margin: auto;
    width: 1200px;
    display: flex;
    justify-content: space-around;
}

.left {
    width: 750px;


}

.right {
    width: 360px;
    height: 100vh;
}

.movie {
    margin-top: 30px;
}

.movie-item {
    width: 160px;
    height: 260px;
    /* background-color: aqua; */
    border: 1px solid #efefef;
    margin-top: 30px;
}

.movie-poster {
    /* background-color: blanchedalmond; */
    width: 100%;
    height: 220px;
    position: relative;
    background-size: 160px 220px;

}

.movie-sale:hover {
    background-color: aqua;
    color: aliceblue;
}

.movie-info {
    width: 100%;
    position: absolute;
    bottom: 0;
    display: flex;
    justify-content: space-between;
    padding-bottom: 10px;

}

.movie-info p {
    padding: 0 10px;
    color: #fff;
}

.frist-office {
    margin-top: 30px;
    display: flex;
    border: 1px solid #efefef;
    height: 80px;
    justify-content: space-between;
    margin-bottom: 15px;
}

.frist-office:hover {
    background-color: #F7F7F7;
}

.frist-office-img {
    width: 120px;
}

.frist-office-info {
    width: 220px;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.other-office {
    width: 100%;
    height: 35px;
    padding: 5px 0;
    display: flex;
    justify-content: space-between;
}

.other-office:hover {
    background-color: #F7F7F7;
}

.other-office span {
    line-height: 35px;
}

.hot-actions {
    display: flex;
    gap: 10px;
    margin-top: 15px;
}

.hot-actions p {
    flex: 1;
    text-align: center;
    line-height: 32px;
    height: 32px;
    border: 1px solid #efefef;
    font-size: 13px;
    color: #333;
    cursor: pointer;
    margin: 0;
}

.hot-actions p:hover {
    background-color: #F7F7F7;
}

/* 抓的时候只把字变淡，仍然点得到：一个看不见入口的按钮比一个慢按钮更难用 */
.hot-actions p.dim {
    color: #bbbbbb;
}

.hot-result {
    font-size: 12px;
    color: #999999;
    margin: 10px 0 0;
    line-height: 18px;
}
</style>