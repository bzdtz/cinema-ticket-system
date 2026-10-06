<template>
    <div>
        <div style="background-image: url('./banner_bg.png'); height: 376px; margin-bottom: 75px;" v-if="hasMovie == true">
            <div style="width: 1200px;height: 100%;margin: auto;display: flex;">
                <div style="margin: 0 30px; position: relative;top: 60px;    border: 4px solid #fff;height: 330px;">
                    <img :src="movie.banner" alt="" width="240" height="330">
                </div>
                <div style="margin: 70px 30px 0 30px;">
                    <div style="color: #fff;">
                        <h1 class="name">{{ movie.name }}</h1>

                        <!-- <a style="margin-top: 10px;" v-for="type in movieType" href="javascript:;"
                            @click.prevent="navigateToFilm(type.movietype.id)" :key="type.movietype.id">
                            {{ type.movietype.typename }} &nbsp;
                        </a> -->
                        <div style="display: flex; ">
                            <div v-for="i in movieType" style="display: flex;margin-right: 10px ">
                                <p class="hoverPointer"  @click.prevent="navigateToFilm(i.movietype.id)" :key="i.movietype.id" style="display: flex;margin-top: 10px;">{{i.movietype.typename}}</p>
                            </div>
                        </div>

                        <div style="display: flex;margin-top: 5px;">
                            <p style="padding-right: 5px;">{{ movie.langue }}</p> / <p style="padding-left: 5px;">
                                117分钟</p>
                        </div>

                        <p style="margin-top: 5px;">{{ movie.releaseTime }} 上映</p>
                        <p style="margin-top: 5px;">{{ movie.region }}</p>

                    </div>
                    <div style="display: flex;position: relative;top: 45px;width: 500px;justify-content: space-between;">
                        <div style="display: flex;flex-direction: column;justify-content: space-between;">
                            <div style="width: 300px;margin-top: 5px;">
                                <el-button color="#756189" size="large" style="padding: 5px 40px;">想看</el-button>
                                <el-button color="#756189" size="large" style="padding: 5px 40px;"
                                    @click="dialogTableVisible = true">评分
                                </el-button>
                            </div>
                            <div>
                                <el-button type="danger" color="#DF2D2D" size="large" @click="toMovie"
                                    style="padding: 5px 60px;">查看更多电影详情
                                </el-button>

                            </div>
                        </div>
                        <div style="color: #fff;">
                            <div>
                                <p style="font-size: 13px;">评分</p>
                                <div>
                                    <div>
                                        <el-rate v-model="movie.score" disabled show-score text-color="#ff9900"
                                            score-template="{value} 分" :max="5" />
                                    </div>
                                </div>

                            </div>
                            <div>
                                <p style="font-size: 13px;">累计票房</p>
                                <div>
                                    <p style="font-size: 30px;">1197万</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="tag" @change="changeCinema" style="margin-bottom: 30px;">
            <div id="app" style="border-bottom: 1px solid rgb(229, 229, 229);">

                <div style="list-style:none;display: flex   ;" class="Types">
                    <div style="position: relative;top: 15px;">地区</div>
                    <div style="display: flex;margin: 10px;">
                        <input type="radio" v-model="address" value="新乡市" @click="getType" id="city1">
                        <label for="city1" style="border-radius: 50px;    padding: 2px 21px;">新乡市</label>
                        <input type="radio" v-model="address" value="郑州市" @click="getType" id="city2">
                        <label for="city2" style="    padding: 2px 21px;border-radius: 50px;">郑州市</label>
                    </div>
                </div>
                <!-- <el-input v-model="address" placeholder="请输入地址"></el-input>
                <el-button @click="getType" type="primary">点击搜索</el-button> -->
            </div>
            <!--                //判断如果为 null则不显示-->
            <div v-if="showDate.length != 0" style="border-bottom: 1px solid #e5e5e5;" class="Types">
                <div style="display: flex;">
                    <div style="position: relative;top: 15px;"> 日期</div>
                    <div v-for="(date, index) in showDate" :key="index" style="display: flex;margin: 10px;">
                        <input type="radio" v-model="query.date.showdate" :value="date" :id="date" >
                        <label :for="date" style="    padding: 2px 21px;border-radius: 50px;width: 100px;"> {{ date.showdate
                        }}</label>
                    </div>
                </div>
            </div>

            <div style="border-bottom: 1px solid #e5e5e5;">

                <div style="display: flex;" >
                    <div style="position: relative;top: 15px;">类型:</div>
                    <div class="Types" style="display: flex;flex-wrap: wrap">
                        <div style="display: flex;margin: 5px;">
                            <input type="radio" v-model="query.brand" value="all" id="alltype" >
                            <label for="alltype" style="    padding: 2px 21px;border-radius: 50px;">全部</label>
                        </div>
                        <div v-for="(item, index) in lis" :key="index" style="display: flex;margin: 10px;">
                            <input type="radio" v-model="query.brand" :value="item" :id="item">
                            <label :for="item" style="    padding: 2px 21px;border-radius: 50px;">{{ item}}</label>
                        </div>
                    </div>

                </div>
            </div>
            <div style="border-bottom: 1px solid #e5e5e5;">

                <div style="display: flex;">
                    <div style="position: relative;top: 15px;width: 90px">区域:</div>
                    <div class="Types" style="display: flex;flex-wrap: wrap">
                        <div style="display: flex;margin: 5px;">
                            <input type="radio" v-model="query.country" value="all" id="sizeall">
                            <label for="sizeall" style="    padding: 2px 15px;border-radius: 50px;">全部</label>
                        </div>
                        <div v-for="(item, index) in lis2" :key="index" style="display: flex;margin: 5px;">
                            <input type="radio" v-model="query.country" :value="item" :id="item">
                            <label :for="item" style="    padding: 2px 15px;border-radius: 50px;width: auto">{{ item }}</label>
                        </div>
                    </div>

                </div>
            </div>
            <div style="border-bottom: 1px solid #e5e5e5;">

                <div style="list-style:none; display: flex;">
                    <div style="position: relative;top: 15px;width: 70px">影厅类型:</div>
                    <div class="Types" style="display: flex;flex-wrap: wrap">
                        <div style="display: flex;margin: 10px;">
                            <input type="radio" v-model="query.type" value="all" id="cinemaALl">
                            <label for="cinemaALl" style="    padding: 2px 21px;border-radius: 50px;">全部</label>
                        </div>
                        <div v-for="(item, index) in lis3" :key="index" style="display: flex;margin: 10px;">
                            <input type="radio" v-model="query.type" :value="item" :id="item">
                            <label :for="item" style="    padding: 2px 21px;border-radius: 50px;">{{ item }}</label>
                        </div>
                    </div>

                </div>
            </div>
        </div>


        <div style="width: 1180px;height: 100%;margin: auto;">

            <div>
                <el-card v-for="cinema in cinemas" :key="cinema.id" style="margin-bottom: 20px;">
                    <div
                        style="display: flex; flex-direction: row; justify-content: space-between; border-bottom: 1px dashed #e5e5e5; padding-bottom: 15px; padding-top: 15px;">
                        <div>
                            <p style="font-size: 20px;">{{ cinema.name }}</p>
                            <p style="color: #999; font-size: 15px; margin-top: 10px;">地址：{{ cinema.specifiedAddress
                            }}</p>
                            <div style="margin-top: 10px;">
                                <el-tag :key="tag" style="margin-right: 10px;">{{  cinema.tag }}
                                </el-tag>
                            </div>
                        </div>
                        <div
                            style="display: flex; flex-direction: row; justify-content: center; align-items: center; padding: 20px;">
                            <div style="display: flex; padding-right: 20px;">
                                <p style="font-size: 20px; color: #f03d37;">{{ cinema.price }}</p>
                                <p style="height: 26px; line-height: 26px; padding: 0 0 0 5px;">起</p>
                            </div>
                            <el-button type="primary" @click="toShow(cinema)" round>选座购票</el-button>
                        </div>
                    </div>
                </el-card>

            </div>



        </div>
        <div style="margin: auto;width: 1200px;display: flex;justify-content: center;align-items: center;">
            <el-pagination :current-page="query.current" :page-size="query.size" :total="totalRows"
                layout="prev, pager, next" @current-change="handlePageChange" style="margin-top: 20px;"></el-pagination>
        </div>
        <el-dialog v-model="dialogTableVisible">
            <div style="display: flex;flex-direction: column;justify-content: center;align-items:center ;">
                <div v-if="value == 0">
                    <p style="font-size: 15px;">请点击星星评分</p>
                </div>

                <el-rate v-model="value" allow-half />
                <el-input v-model="textarea" :rows="2" type="textarea" placeholder="Please input" />
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="dialogFormVisible = false">
                        Confirm
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import {
    provinceAndCityData,
    pcTextArr,
    regionData,
    pcaTextArr,
    codeToText,
} from "element-china-area-data";
import axios from 'axios'

export default {

    data() {
        return {
            showDate: [],
            cid: -1,

            movieId: null,
            address: "新乡市",
            hasMovie: false,
            movie:
            {
                // name: '',
                // tag: '',
                // region: '',
                // movie_length: '',
                // score: 9.6 / 2,
                // box_office: "",
                // synopsis: '',
                // langue: "",

            },
            query:
            {
                brand: "all",
                country: "",
                type: "",
                city: "",
                userDate: "",
                current: 1,
                size: 8,
                mid: '',
                date: {
                    showdate: '',
                }

            }
            ,
            value: 0,
            dialogTableVisible: false,
            lis: [],
            lis2: [],
            lis3: [],

            totalPages: 0,
            totalRows: 0,
            cinemas: [],
            movieType: [],
            pcaTextArr,

        }

    },
    methods: {
        changeCinema() {
            // this.cinemas = [];
            if (this.movieId) {
                console.log("当前有电影id  调用 有时间联查的 =============================")
                this.getCinemasTwo();
            } else {

                console.log("==============================123456789789788998779987")
                this.getCinemas();
            }
        },


        getShowDate() {
            // /app/showtimes/date/1/1
            axios({
                method: 'get',
                url: '/app/showtimes/date/' + this.cid + '/' + this.movieId
            }).then((res) => {
                // console.log("当前的电影的观看日期")
                // console.log(res);
                if (res.data.code == 200) {

                    // console.log("我带着电影id 返回了当前可以上映的日期")
                    this.showDate = res.data.data;


                }

            })
        },
        navigateToFilm(id) {
            this.$router.push({
                path: 'films',
                query: { typeId: id }
            });
        },
        getMovieType() {
            this.$axios({
                method: 'get',
                url: "/app/movie/type/" + this.movieId
            }).then((res) => {
                // console.log("这是当前电影所拥有的类型")
                // console.log(res);
                if (res.data.code = 200) {
                    this.movieType = res.data.data;
                }
            })
        },
        getMovie() {
            this.$axios({
                method: 'get',
                url: '/app/movie/' + this.movieId
            }).then((res) => {
                // console.log("获取到数据了好开心")
                // console.log(res);
                if (res.data.code == 200) {
                    this.movie = res.data.data;
                    this.movie.score /= 2
                }
            })
        },
        toMovie() {
            this.$router.push({
                path: '/movie',
                query: {
                    id: this.movieId
                }
            })
        },
        toShow(row) {
            // console.log("展示row的信息")
            // console.log(row)
            this.$router.push({ path: '/cinemashow', query: { id: row.id } });
        },
        handlePageChange(number) {
            // console.log("当前的页数")
            // console.log(number)
            this.query.current = number;
            this.getCinemas();
        },
        getType() {
            axios({
                method: 'get',
                url: "/app/cinema/get/" + this.address
            }).then((res) => {
                // console.log("数据类型返回来了")
                // console.log(res);
                if (res.data.code == 200) {
                    this.lis = res.data.data[0];
                    this.lis2 = res.data.data[1];
                    this.lis3 = res.data.data[2];
                }

                this.getCinemas()

            })
        },
        getCinemas() {
            console.log("查询条件")
            if (this.query.brand == "all") {
                console.log("当前的品牌是全部")
                this.query.brand = "all"
            }
            this.query.city = this.address

            // console.log("输出一下，我目前所具有的 所有查询条件")
            // console.log(this.query);
            axios({
                method: "post",
                url: '/app/cinema/getCinema',
                data: this.query
            }).then((res) => {
                // console.log("我有电影id了 限制我  ++++++++++++++++++++++获取影院的列表了");
                // console.log(res);
                if (res.data.code == 200) {
                    this.cinemas = res.data.data.data;
                    this.totalRows = res.data.data.totalRows;
                    this.totalPages = res.data.data.totalPages;
                }

            })
        },
        getCinemasTwo() {
            this.cinemas = [];
            // console.log("查询条件")
            if (this.query.brand == "all") {
                console.log("当前的品牌是全部")
                this.query.brand = "all";
            }
            this.query.city = this.address

            this.query.mid = this.movieId;
            this.query.userDate = this.query.date.showdate.showdate;
            console.log(this.query.date.showdate.showdate)
            console.log("输出一下，我目前所具有的 所有查询条件")
            console.log(this.query);
            axios({
                method: "post",
                url: '/app/showtimes/getCinema',
                data: this.query
            }).then((res) => {
                console.log("我有电影id了 限制我  ++++++++++++++++++++++获取影院的列表了");
                console.log(res);
                if (res.data.code == 200) {
                    let showTime = res.data.data.data
                    console.log("+++++++++++++++===========================")

                    showTime.forEach((item) => {
                        this.cinemas.push(item.cinema)
                    })

                    console.log("展示一下当前的 cinemas  ==================================================sssssssss")
                    console.log(this.cinemas);
                    // this.cinemas = res.data.data.data;
                    this.totalRows = res.data.data.totalRows;
                    // this.totalPages = res.data.data.totalPages;
                }

            })
        }
    },
    computed: {
        showscore(score) {
            return score / 2
        }
    },
    created() {
        this.movieId = this.$route.query.id;
        // if (this.movieId) {
        //     this.getShowDate();
        //     this.getCinemasTwo();
        // }



    },
    mounted() {
        this.getType();
        // this.movieId = this.$route.query.id;

        if (this.movieId) {
            console.log("获取到movieId了")
            console.log(this.movieId)
            this.hasMovie = true; //有电影id
            this.getMovie();
            this.getMovieType();
            this.getShowDate();
            this.getCinemasTwo();
        }
        else {
            this.getCinemas()
        }
    }
}
</script>

<style>
.Types input {
    visibility: hidden;
    width: 0;
    height: 0;
}

.Types label {
    display: flex;
    width: auto;
    height: 28px;
    line-height: 30px;
    justify-content: center;
    align-items: center;

}

.Types input:checked+label {
    background-color: rgb(0, 149, 246);
    color: #fff;
    border-radius: 2px;
}




.name {
    width: 900px;
    margin-top: 0;
    font-size: 26px;
    line-height: 32px;
    font-weight: 700;
    margin-bottom: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    max-height: 64px;
}

.el-button--text {
    margin-right: 15px;
}

.el-select {
    width: 300px;
}

.el-input {
    width: 300px;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}


.tag {
    margin: auto;
    width: 1120px;
    margin-top: 40px;
    border: 1px solid #e5e5e5;
    padding: 0 20px;
}


.tag li {
    padding: 10px 0;
    display: flex;
    flex-wrap: wrap;
    margin-left: 40px;
}

.tag li ul {

    padding: 3px 9px;
    margin-left: 12px;
    font-size: 14px;
}



.tagList {
    margin-left: 10px;
}

.tagList input {
    visibility: hidden;
    width: 0;
}

.tagList label {
    flex-shrink: 1;
    width: 100px;
    height: 30px;
    line-height: 30px;
    border-radius: 5px;
}

.tagList input:checked+label {

    background-color: rgb(0, 149, 246);
    color: #fff;
    border-radius: 2px;
}
.hoverPointer:hover{
        cursor:pointer;
    }
</style>