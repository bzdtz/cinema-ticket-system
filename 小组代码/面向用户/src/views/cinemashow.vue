<template>
    <div>
        <div style="background-image: url('./banner_bg.png'); height: 330px; margin-bottom: 75px;">
            <div style="width: 1200px;height: 100%;margin: auto;display: flex;">
                <div style="margin: 0 30px; position: relative;top: 55px;    border: 4px solid #fff;height: 330px;">
                    <img src="cinema.png" alt="" width="292" height="292">
                </div>
                <div style="margin: 35px 30px 0 30px;">
                    <div style="color: #fff;">
                        <h1 style="    margin: 0;font-size: 26px;margin-bottom: 9px;font-weight: 400;">{{ cinema.name }}
                        </h1>
                        <p style="margin-top: 0px;font-size: 14px;">{{ cinema.specifiedAddress }}</p>
                        <p style="margin-top: 5px;font-size: 14px;">电话：{{ cinema.phone }}</p>
                        <p class="group-title" style="margin-top: 20px;font-size: 14px;margin-bottom: 10px;">影院服务 </p>
                        <el-col>
                            <el-row style="margin-top: 5px;">
                                <el-tag
                                        style="background-color: transparent;color: #fff;font-size: 10px;padding: 5px 15px;border: 1px solid hsla(0,0%,100%,.6);max-width: 70px;"
                                        size="small">退
                                </el-tag>
                                <p style="font-size: 10px;text-align: center;line-height: 20px;padding-left: 10px;">
                                    未取票用户放映前60分钟可退票</p>
                            </el-row>
                            <el-row style="margin-top: 5px;">
                                <el-tag
                                        style="background-color: transparent;color: #fff;font-size: 10px;padding: 5px 15px;border: 1px solid hsla(0,0%,100%,.6);max-width: 70px;"
                                        size="small">3D眼镜免押金
                                </el-tag>
                                <p style="font-size: 10px;text-align: center;line-height: 20px;padding-left: 10px;">
                                    免押金</p>

                            </el-row>
                            <el-row>
                                {{ cinema.service}}
                            </el-row>
                        </el-col>
                    </div>


                </div>
                <div style="position: relative;top: 140px;left: 50px;">
                    <img :src="cinema.img" alt="" width="180" height="100px">
                </div>
            </div>
        </div>
    </div>
    <div style="width: 1200px;height: 100%;margin: auto;display: flex;flex-direction: column;">
        <!--        <el-breadcrumb :separator-icon="ArrowRight">-->
        <!--            <el-breadcrumb-item :to="{ path: '/' }">码上电影</el-breadcrumb-item>-->
        <!--            <el-breadcrumb-item :to="{ path: '/cinemas' }">影院</el-breadcrumb-item>-->
        <!--            <el-breadcrumb-item>{{cinema.name}}</el-breadcrumb-item>-->

        <!--        </el-breadcrumb>-->
        <el-breadcrumb>
            <el-breadcrumb-item :to="{ path: '/' }">码上电影</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/cinemas' }">影院</el-breadcrumb-item>
            <el-breadcrumb-item>{{ cinema.name }}</el-breadcrumb-item>

        </el-breadcrumb>
        <!--        <div style="margin-top: 30px;">-->
        <!--            <el-row style=" height: 280px;-->
        <!--                            padding: 22px 5px;-->
        <!--                            -webkit-box-sizing: border-box;-->
        <!--                            box-sizing: border-box;-->
        <!--                            overflow: hidden;-->
        <!--                            -webkit-box-shadow: inset 0 0 100px 80px #ededed;-->
        <!--                            box-shadow: inset 0 0 100px 80px #ededed;-->
        <!--                            position: relative;">-->
        <!--                <div :style="mobileCss">-->
        <!--                    <div ref="list" :class="{ 'lis': true, 'active': clickmovie }" @click="clickmovie = !clickmovie"-->
        <!--                        v-for="i in 10">-->
        <!--                        <img src="1.jpg" alt="">-->
        <!--                    </div>-->

        <!--                </div>-->
        <!--                <span @click="mobile(1020)" style="background-image: url('left.png');display: inline-block;-->
        <!--                                                            position: absolute;-->
        <!--                                                            width: 30px;-->
        <!--                                                            height: 100%;-->
        <!--                                                            top: 0;-->
        <!--                                                            background-repeat: no-repeat;-->
        <!--                                                            background-position: 50%;"></span>-->
        <!--                <span @click="mobile(-1020)" style="background-image: url('right.png');display: inline-block;-->
        <!--                                                            position: absolute;-->
        <!--                                                            width: 30px;-->
        <!--                                                            height: 100%;-->
        <!--                                                            top: 0;-->
        <!--                                                            background-repeat: no-repeat;-->
        <!--                                                            background-position: 50%;right: 0;"></span>-->
        <!--            </el-row>-->
        <!--        </div>-->

        <!--        <div style="margin-top: 30px;">-->
        <!--            <el-row style="height: 280px; padding: 22px 5px; -webkit-box-sizing: border-box;-->
        <!--                  box-sizing: border-box; overflow: hidden; -webkit-box-shadow: inset 0 0 100px 80px #ededed;-->
        <!--                  box-shadow: inset 0 0 100px 80px #ededed; position: relative;">-->
        <!--                <div :style="mobileCss">-->
        <!--                    <div-->
        <!--                            ref="list"-->
        <!--                            :class="{ 'lis': true, 'active': clickmovie }"-->
        <!--                            @click="clickmovie = !clickmovie"-->
        <!--                            v-for="(movie, index) in movies"-->
        <!--                            :key="index"-->
        <!--                    >-->
        <!--                        <img :src="movie.banner" alt="xx">-->
        <!--                    </div>-->
        <!--                </div>-->
        <!--                <span-->
        <!--                        @click="mobile(1020)"-->
        <!--                        style="background-image: url('left.png');display: inline-block; position: absolute; width: 30px;-->
        <!--               height: 100%; top: 0; background-repeat: no-repeat; background-position: 50%;"-->
        <!--                ></span>-->
        <!--                <span-->
        <!--                        @click="mobile(-1020)"-->
        <!--                        style="background-image: url('right.png');display: inline-block; position: absolute; width: 30px;-->
        <!--               height: 100%; top: 0; background-repeat: no-repeat; background-position: 50%;right: 0;"-->
        <!--                ></span>-->
        <!--            </el-row>-->
        <!--        </div>-->

        <div style="margin-top: 30px;">
            <el-row style="height: 280px; padding: 22px 5px; -webkit-box-sizing: border-box;
          box-sizing: border-box; overflow: hidden; -webkit-box-shadow: inset 0 0 100px 80px #ededed;
          box-shadow: inset 0 0 100px 80px #ededed; position: relative;">
                <div :style="mobileCss">
                    <div ref="list" :class="{ 'lis': true }" v-for="(movie, index) in movies" :key="index">
                        <!--                        <img :src="movie.banner" alt="">-->
                        <img :src="movie.banner" alt="" :class="{ 'lis': true, 'active': clickmovies[index] }"
                             @click="toggleActive(index, movie.id)"/>
                    </div>
                </div>
                <span @click="mobile(1020)" style="background-image: url('left.png');display: inline-block; position: absolute; width: 30px;
         height: 100%; top: 0; background-repeat: no-repeat; background-position: 50%;"></span>
                <span @click="mobile(-1020)" style="background-image: url('right.png');display: inline-block; position: absolute; width: 30px;
         height: 100%; top: 0; background-repeat: no-repeat; background-position: 50%;right: 0;"></span>
            </el-row>
        </div>


        <div>
            <div style="display: flex;margin-top: 20px;">
                <div style="font-size: 30px ;margin:  0 15px;">{{ movie.name }}</div>
                <div style="display: flex; font-size: 24px;color:#faaf00 ;text-align: end;align-items: end;">{{
                    movie.score
                    }}
                </div>
                <div style="display: flex;align-items: end;">分</div>
                <div
                        style="display: flex; font-size: 24px;color:#faaf00 ;text-align: end;align-items: end;margin-left: 20px;">
                    {{ movie.wantNumber }}
                </div>
                <div style="display: flex;align-items: end;">人想看</div>
            </div>
            <div style="display: flex;padding:  15px 0 30px 15px; border-bottom: 1px solid #e5e5e5;">
                <div style="color: #999;">时长 :</div>
                <div>{{ movie.movieLength }}分钟</div>
                <div style="color: #999;margin-left: 40px;">类型 :</div>
                <div v-for="type in types">
                    <span>{{ type.movietype.typename }} </span> &nbsp;
                </div>
                <div style="color: #999;margin-left: 40px;">主演 :</div>
                <div v-for="acter in character">
                    <span>{{ acter.actor.name }}</span> &nbsp;
                </div>
            </div>

        </div>
        <div style="padding-top: 20px;">
            <el-row style="margin-bottom: 10px;">
                <div style="color: #999;display: flex;align-items: center;">
                    观影时间
                </div>
                <!--                <div class="mb-2 flex items-center text-sm" v-for="(date,index) in showDate" :key="date.id">-->
                <!--                    <el-radio-group v-model="selectedRadios[index]" class="ml-4">-->
                <!--                        <el-radio size="large" :value="date.showdate">{{ date.showdate }}</el-radio>-->
                <!--                    </el-radio-group>-->
                <!--                </div>-->
                <div style="display: flex">
                    <!-- <el-radio-group v-model="selectedValue"> -->
                    <div v-for="date in showDate" class="Types" >
                        <input type="radio" :key="date.id" @click="getTime(date.showdate)" :id="date.showdate"
                               :value="date.showdate" :checked=" date.showdate == clicktime " />
                        <label :for="date.showdate" style="padding: 2px;border-radius: 50px;width: 100px;">{{
                            date.showdate
                            }}</label>
                    </div>

                    <!-- </el-radio-group> -->

                </div>
            </el-row>


            <!--            <el-table :data="movieLis" style="width: 100%" :row-class-name="tableRowClassName">-->
            <!--                <el-table-column prop="time" label="放映时间" />-->
            <!--                <el-table-column prop="lv" label="语言版本" />-->
            <!--                <el-table-column prop="hall" label="放映厅" />-->
            <!--                <el-table-column prop="price" label="售价（元）" />-->
            <!--                <el-table-column prop="" label="选座购票">-->
            <!--                    <el-button type="danger" round style="padding: 10px;width: 100px;height: 30px;">Danger</el-button>-->
            <!--                </el-table-column>-->

            <!--            </el-table>-->
            <el-table :data="movieLis" style="width: 100%;height: 500px;">
                <el-table-column prop="showtime" label="放映时间"/>
                <el-table-column prop="movie.langue" label="语言版本"/>
                <el-table-column prop="hall.hallName" label="放映厅"/>
                <el-table-column prop="sale" label="售价（元）"/>
                <el-table-column prop="" label="选座购票">
                    <template #default="scope">
                        <el-button type="danger" round style="padding: 10px;width: 100px;height: 30px;"
                                   @click="toxseats(scope.row)">购票
                        </el-button>

                    </template>
                </el-table-column>

            </el-table>
        </div>
    </div>
</template>
<script>
    import axios from 'axios'

    export default {
        data() {
            return {
                queryCidMid: {
                    cinemaId: 0,
                    movieId: 0,
                    showDate: ''
                },
                selectedValue: null,
                showDate: [],
                character: [],
                movie: {},
                types: [],
                index: 0,  //如果传电影id后，那么 index 为该值，如果没有传则 为0
                movies: [],
                clickmovies: [],
                radio: null,
                cinema: {},
                cinemaId: 0,
                mid: 0,
                hasMovie: true,
                shows: null,
                mobileCss: {
                    "white-space": "nowrap",
                    position: "relative",
                    display: "table",
                    // left: 0,
                    "-webkit-transition": "left .2s ease",
                    transition: "left .2s ease",
                    left: -1020 + "px",
                },
                clicktime: "",

                // mobileCss: {
                //     "white-space": "nowrap",
                //     position: "relative",
                //     display: "table",
                //     // left: 0,
                //     "-webkit-transition": "left .2s ease",
                //     transition: "left .2s ease",
                //     left: "-1020px", // Initialize with a string value
                // },
                // clickmovie: false,
                movieLis: [
                    {
                        time: '15:40',
                        lv: '国语2D',
                        hall: '六号厅',
                        price: '34.8'

                    },

                ]
            }
        },
        methods: {
            getTime(showDate) {
                this.clicktime=showDate
                this.queryCidMid.cinemaId = this.cinemaId
                this.queryCidMid.movieId = this.mid
                // this.queryCidMid.cinemaId=this.cinemaId

                this.queryCidMid.showTime = showDate
                console.log("展示的参数")

                console.log(this.queryCidMid)

                axios({
                    method: 'post',
                    url: '/app/showtimes/showtime',
                    data: this.queryCidMid
                }).then((res) => {
                    console.log(res);
                    if (res.data.code == 200) {
                        if(res.data.data.length == 0){
                            return
                        }
                        console.log(res.data);
                        this.shows = res.data.data[0].id
                        console.log(res)
                        this.movieLis = res.data.data
                    }
                })
            },
            getShowDate(cid, mid) {
                // /app/showtimes/date/1/1
                axios({
                    method: 'get',
                    url: '/app/showtimes/date/' + cid + '/' + mid
                }).then((res) => {
                    console.log("当前的电影的观看日期")
                    console.log(res);
                    if (res.data.code == 200) {

                        this.showDate = res.data.data;
                        console.log("tttttttttttt");

                        console.log(this.showDate);
                        this.clicktime = this.showDate[0].showdate
                        this.getTime(this.showDate[0].showdate)


                    }

                })
            },
            getCharacter(id) {
                axios({
                    method: 'get',
                    url: '/app/actor-movie/detail/' + this.mid
                }).then((res) => {
                    console.log("当前的电影的演职人员")
                    console.log(res);
                    if (res.data.code == 200) {

                        this.character = res.data.data.slice(0, 3);


                    }

                })
            },
            getMovie(id) {
                console.log("我现在在getMovie的方法中")
                console.log(this.mid)
                axios({
                    method: 'get',
                    url: '/app/movie/' + this.mid
                }).then((res) => {
                    console.log("当前的电影")
                    console.log(res);
                    if (res.data.code == 200) {
                        this.movie = res.data.data

                    }

                })
            },
            getMovieType(id) {
                axios({
                    method: 'get',
                    url: '/app/movie/type/' + this.mid
                }).then((res) => {
                    console.log("当前的电影类型")
                    console.log(res);
                    if (res.data.code == 200) {
                        this.types = res.data.data

                    }

                })
            },

            getMovieBanner() {
                axios({
                    method: 'get',
                    url: "/app/movie/getBanner/" + this.cinemaId
                }).then((res) => {
                    console.log("展示当前影院能购买的电影的海报信息")
                    console.log(res)
                    if (res.data.code == 200) {
                        this.movies = res.data.data
                        console.log("展示一下当前获取的movies")
                        console.log(this.movies);
                        this.$nextTick(() => {
                            if (this.movies.length > 0) {
                                // 假设初始时选择第一张图片
                                this.toggleActive(0, this.movies[0].id);

                                this.mobile(1020);
                            }

                        })


                    }

                }).catch(reason => {

                })

            },
            getCinema() {
                axios({
                    method: 'get',
                    url: "/app/cinema/getByid/" + this.cinemaId
                }).then((res) => {
                    console.log("展示当前影院的信息")
                    console.log(res)
                    if (res.data.code == 200) {
                        this.cinema = res.data.data;
                        console.log("影院的信息")
                        console.log(this.cinema)

                    }

                }).catch(reason => {

                })
            },
            mobile(data) {
                // let height= list.value.clientWidth;
                // console.log(height);
                this.mobileCss.left = parseInt(this.mobileCss.left) + data + "px"
                // console.log(this.mobileCss.left);

                // this.mobileCss.left = `${parseInt(this.mobileCss.left) + data}px`;

            },
            toggleActive(index, movieId) {
                // Toggle the click state for the clicked movie
                console.log("点击事件")
                console.log(index)
                this.clickmovies[index] = !this.clickmovies[index];
                this.clickmovies = this.clickmovies.map((_, i) => i === index);

                //清空单选框
                this.selectedValue = null;

                this.movieLis = null;

                this.mid = movieId
                //当前事件点击后，会获取当前的电影id
                console.log("当前的电影id")
                console.log(movieId)
                this.getMovieType();
                this.getMovie();
                this.getCharacter();

                this.getShowDate(this.cinemaId, movieId);
                this.getTime(null)
            },
            toxseats() {

                //从本地获取id 然后从后端查询吧

                let loginUserStr = localStorage.getItem("loginUser");
                console.log("-----------------------");
                let loginUser = JSON.parse(loginUserStr);
                console.log(loginUser);
                this.$axios({
                    method: 'get',
                    url: '/app/user/getStatus/' + loginUser.id
                }).then(res => {

                    console.log("=============================");
                    console.log(res);
                    if (res.data.code == 500) {
                        this.$message.error("您的权限被禁用，不能购票");
                        return;
                    } else {
                        console.log(this.queryCidMid);
                        this.$router.push({
                            path: '/xseats',
                            query: {
                                cinemaId: this.queryCidMid.cinemaId,
                                movieId: this.queryCidMid.movieId,
                                showtimes: this.shows
                            }
                        });

                    }
                }).catch(e => {

                })

            }
        },
        mounted() {
            this.cinemaId = this.$route.query.id;
            console.log(this.cinemaId);
            if (this.cinemaId) {
                this.getCinema();
                this.getMovieBanner();

            }
        }
    }
</script>

<style>
    .group-title::after {
        border-top: 1px solid hsla(0, 0%, 100%, .7);
        display: block;
        content: "";
        position: relative;
        top: -10px;
        left: 70px;
    }

    .lis.active {
        -webkit-transform: scale(1);
        -ms-transform: scale(1);
        transform: scale(1);
        border-color: #f24030;
    }

    .lis {
        width: 162px;
        height: 227px;
        border: 4px solid #fff;
        -webkit-box-shadow: 0 1px 3px 0 hsla(0, 0%, 66%, .5);
        box-shadow: 0 1px 3px 0 hsla(0, 0%, 66%, .5);
        display: inline-block;
        -webkit-transform-origin: 50%;
        -ms-transform-origin: 50%;
        transform-origin: 50%;
        -webkit-transform: scale(.82);
        -ms-transform: scale(.82);
        transform: scale(.82);
        -webkit-transition: -webkit-transform .1s;
        transition: -webkit-transform .1s;
        transition: transform .1s;
        transition: transform .1s, -webkit-transform .1s;
        word-spacing: 0;
    }

    .Types {
        display: flex;
    }

    .Types input {
        visibility: hidden;
        width: 0;
        height: 0;
    }

    .Types label {
        display: flex;
        width: 67px;
        height: 28px;
        line-height: 30px;
        justify-content: center;
        align-items: center;
    }

    .Types input:checked + label {
        background-color: rgb(0, 149, 246);
        color: #fff;
        border-radius: 2px;
    }
</style>