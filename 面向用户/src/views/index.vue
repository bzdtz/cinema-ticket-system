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
                                <div>
                                    <img :src="movie.tag.tag" style="position: relative;top: 10px;left: -3px;">
                                </div>
                                <div class="movie-info">
                                    <p>{{ movie.name }}</p>
                                    <p>{{ movie.score }}</p>
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
                                <div>
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
                    <div>
                        <h2 style="margin: 0;">前10票房</h2>
                    </div>
                    <div v-for=" i in boxOffice">
                        <div class="frist-office" v-if="i.index == 1">
                            <div class="frist-office-img" style="backgroundImage: url('top10.jpg') ;   background-size: 120px 100px;">
                                <img src="frist.png" alt="">
                            </div>
                            <div class="frist-office-info">
                                <p style="font-size: 22px;">{{ i.title }}</p>
                                <p style="color: red;">{{ i.sumBoxDesc }}万</p>
                            </div>

                        </div>
                        <div class="other-office" v-else>
                            <div style="width: 65%;">
                                <i :style="{ color: (i.index == 2 || i.index == 3 ? 'red' : '#999') }"
                                    style="line-height: 35px;font-size: 20px;width: 30px; margin-right: 10px;">{{ i.index
                                    }}</i>
                                <span style="width: 230px;font-size: 18px;">{{ i.title }}</span>
                            </div>
                            <div style="display:flex ;">
                                <span style="font-size: 10px;">{{ i.releaseInfo }}</span>
                                <div style="width: 65px;text-align: center;">
                                    <i style="font-size: 15px;line-height: 35px;color: red;">{{ i.sumBoxDesc }}</i>

                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="want">

            </div>
            <div class="top100">
                <div>
                    <div class="movie">
                        <h2 style="margin: 0;">TOP100</h2>
                    </div>
                    <div v-for="i, index in top">
                        <div class="frist-office" v-if="index == 0">
                            <div class="frist-office-img" :style="{ backgroundImage: 'url(' + i.img + ')' }">
                                <img src="top.png" alt="">
                            </div>
                            <div class="frist-office-info">
                                <p style="font-size: 22px;">{{ i.name }}</p>
                                <p style="color: red;">{{ i.sorce }}分</p>
                            </div>

                        </div>
                        <div class="other-office" v-else>
                            <i style="font-size: 20px;line-height: 35px;">{{ index + 1 }}</i>
                            <span style="width: 65%;font-size: 18px;">{{ i.name }}</span>
                            <span style="font-size: 14px;color: red;">{{ i.sorce }}分</span>
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
            boxOffice: [
                {
                    "index": 1,
                    "title": "年会不能停！",
                    "releaseInfo": "上映6天",
                    "sumBoxDesc": "3.65亿",
                    img:"top10.png"
                },
                {
                    "index": 2,
                    "title": "金手指",
                    "releaseInfo": "上映5天",
                    "sumBoxDesc": "2.50亿"
                },
                {
                    "index": 3,
                    "title": "潜行",
                    "releaseInfo": "上映6天",
                    "sumBoxDesc": "2.44亿"
                },
                {
                    "index": 4,
                    "title": "一闪一闪亮星星",
                    "releaseInfo": "上映5天",
                    "sumBoxDesc": "6.26亿"
                },
                {
                    "index": 5,
                    "title": "三大队",
                    "releaseInfo": "上映20天",
                    "sumBoxDesc": "5.89亿"
                },
                {
                    "index": 6,
                    "title": "海王2：失落的王国",
                    "releaseInfo": "上映15天",
                    "sumBoxDesc": "3.64亿"
                },
                {
                    "index": 7,
                    "title": "非诚勿扰3",
                    "releaseInfo": "上映5天",
                    "sumBoxDesc": "6197.3万"
                },
                {
                    "index": 8,
                    "title": "人民万岁",
                    "releaseInfo": "上映9天",
                    "sumBoxDesc": "981.7万"
                },
                {
                    "index": 9,
                    "title": "时代巡回演唱会",
                    "releaseInfo": "上映4天",
                    "sumBoxDesc": "5316.2万"
                },
                {
                    "index": 10,
                    "title": "志愿军：雄兵出击",
                    "releaseInfo": "上映98天",
                    "sumBoxDesc": "8.59亿"
                }
            ]
            ,
            top: [
                // {
                //     img: "323ec4a8c424d312f1d4088686e7d239.jpg",
                //     sort: 1,
                //     name: '海王2：失落的王国',
                //     sorce: '9.6',
                // },
                // {
                //     img: "323ec4a8c424d312f1d4088686e7d239.jpg",
                //     sort: 2,
                //     name: '海王2：失落的王国',
                //     sorce: '9.6',
                // }, {
                //     img: "323ec4a8c424d312f1d4088686e7d239.jpg",
                //     sort: 2,
                //     name: '海王2：失落的王国',
                //     sorce: '9.6',
                // }, {
                //     img: "323ec4a8c424d312f1d4088686e7d239.jpg",
                //     sort: 2,
                //     name: '海王2：失落的王国',
                //     sorce: '9.6',
                // }, {
                //     img: "323ec4a8c424d312f1d4088686e7d239.jpg",
                //     sort: 2,
                //     name: '海王2：失落的王国',
                //     sorce: '9.6',
                // },
                {
                    img: "323ec4a8c424d312f1d4088686e7d239.jpg",
                    sort: 2,
                    name: '海王2：失落的王国',
                    sorce: '9.6',
                },
            ]


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
        // getboxOffice() {
        //     this.$axios({
        //         method: 'get',
        //         url: 'https://api.lolimi.cn/API/piao/dy.php?type=json'
        //     }).then((res) => {
        //         // console.log("输出票房前10");
        //         console.log(res.data.data);
        //         this.boxOffice = res.data.data;
        //
        //     })
        // },
        getTopMovie() {

            // this.$axios({
            //     method: 'get',
            //     url: 'https://api.lolimi.cn/API/piao/dy.php?type=json'
            // }).then(response => {
            //     console.log("输出top250的电影");
            //     console.log(response);
            // })
            //     //失败返回
            //     .catch(error => {
            //         console.log(error);
            //     })


        }
    },
    mounted() {
        this.getHotMovie();
        this.getWantMovie();
        this.getTopMovie();
        // this.getboxOffice();
    },
    computed: {
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
</style>