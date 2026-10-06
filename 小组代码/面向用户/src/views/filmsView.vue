<template>
    <div>
        <div class="subnav">
            <div>
                <p>正在热映</p>
            </div>
            <div>
                <p>即将上映</p>
            </div>
            <div>
                经典影片
            </div>
        </div>
        <div class="tag" @change="getQuery">
            <div style="border-bottom: 1px solid #e5e5e5;display: flex; margin-top: 10px;padding-bottom: 5px">
                <div style="width: 75px;padding-top: 5px;">类型:</div>
                <div style="display: flex;flex-wrap: wrap;" class="Types">
                    <div v-for="(item, index) in lis" style="display: flex;">
                        <input type="radio" v-model="query.typeId" :value="item.id" :id="item.id" style="margin: 0;">
                        <label :for="item.id" style="padding: 2px;border-radius: 50px;">{{ item.typename }}</label>
                    </div>
                </div>
            </div>
            <div style="border-bottom: 1px solid #e5e5e5;display: flex;margin-top: 10px;padding-bottom: 10px;">
                <div style="display: flex;flex-wrap: wrap;" class="Types">
                    <div style="width: 45px;padding-top: 5px;">区域:</div>
                    <div v-for="(item, index) in regionList" :key="index" style="display: flex;">
                        <input type="radio" v-model="query.region" :value="item" :id="item" style="margin: 0;">
                        <label :for="item" style="padding: 2px;border-radius: 50px;">{{ item }}</label>
                    </div>
                </div>
            </div>
            <div style="border-bottom: 1px solid #e5e5e5;display: flex;margin-top: 10px;padding-bottom: 10px;">
                <div style="display: flex;flex-wrap: wrap;" class="Types">
                    <div style="width: 45px;padding-top: 5px;">年代:</div>
                    <div v-for="(item, index) in timeList" :key="index" style="display: flex;">
                        <input type="radio" v-model="query.year" :value="item" :id="item" style="margin: 0;">
                        <label :for="item" style="padding: 2px;border-radius: 50px;">{{ item }}</label>
                    </div>
                </div>
            </div>
        </div>

        <div style="    margin: auto;width: 1120px;">
            <el-radio-group v-model="query.radio" @change="getQuery">
                <el-radio :label="3"> 按热门排序 </el-radio>
                <el-radio :label="6"> 按时间排序 </el-radio>
                <el-radio :label="9"> 按评价排序</el-radio>
            </el-radio-group>
        </div>
    </div>
    <div class="films-list">



        <div class="card" v-for="film in films" :key="film.index" @click="enter(film.id)">
            <div class="image" :style="{ backgroundImage: 'url(' + film.banner + ')' }">
                <span class="text"> {{ film.score }}</span>

            </div>
            <span class="title">{{ film.name }}</span>
            <span class="price">{{ showtime(film.releaseTime) }}上映</span>
        </div>
    </div>

    <div style="width: 1200px;margin: auto;display: flex;justify-content: center;align-items: center;">
        <el-pagination :current-page="query.current" :page-size="query.size" :total="totalRows" layout="prev, pager, next"
            @current-change="handlePageChange" style="margin-top: 20px;"></el-pagination>
    </div>
</template>

<script>

import moment from "moment";

export default {
    data() {
        return {
            hoverIndex: null,
            query: {
                typeId: 1,
                region: '全部',
                year: null,
                radio: '',
                box_office: '',
                score: '',
                releaseTime: '',
                current: 1,
                size: 10
            },
            totalRows: null,
            timeList: [],
            lis: [],
            regionList: [],
            films: [
                // {
                //     name: '蜡笔小新',
                //     type: ["爱情", "动画"],
                //     score: '9.5',
                //     time: '2023-12-30',
                //     actor: "匿名",
                //     img: "fb73865111ef2a3139c9fdffb6b1c2b0210e0.jpg"
                // },
            ]
        }
    },
    methods: {
        handlePageChange(number) {
            console.log("当前的页数")
            console.log(number)
            this.query.current = number;
            this.getQuery();
        },
        enter(movieId) {
            console.log(movieId)
            console.log("进入电影详情");
            // let id = Object.assign({}, movieId);
            this.$router.push({ name: 'movie', query: { id: movieId } });

        },

        showHoverInfo(index) {
            this.hoverIndex = index;
        },
        hideHoverInfo() {
            this.hoverIndex = null;
        },

        getQuery() {

            if (this.query.radio == '3') {
                this.query.box_office = 1;
                this.query.score = '',
                    this.query.releaseTime = ''
            }

            if (this.query.radio == '6') {
                this.query.releaseTime = '2023-12-24';
                this.query.box_office = '',
                    this.query.score = ''
            }

            if (this.query.radio == '9') {
                this.query.score = 3
                this.query.releaseTime = '',
                    this.query.box_office = ''
            }

            console.log(this.query);
            //获取数据
            this.$axios({
                method: 'post',
                url: '/app/movie/page',
                data: this.query
            }).then((res) => {
                console.log("按照某某某去排序")
                console.log(res);
                this.films = res.data.data.data;
                this.totalRows = res.data.data.totalRows
                console.log(this.films)
            })
        },
        getTimeList() {

            let currentYear = new Date().getFullYear();

            // 创建一个空数组来存储年份
            let yearList = [];

            // 假设我们想要添加接下来的5年（包括当前年份）
            for (let i = 0; i < 9; i++) {
                yearList.push(currentYear + i);
            }
            console.log(yearList)

            this.timeList = yearList.sort().reverse();

        },
        getList() {
            this.$axios({
                method: 'get',
                url: '/app/movietype/get'
            }).then((res) => {
                console.log("返回的是电影的类型")
                console.log(res.data.data);
                this.lis = res.data.data

            })
        },
        getRegionList() {
            this.$axios({
                method: 'get',
                url: '/app/movietype/region'
            }).then((res) => {
                console.log("返回的是电影的类型")
                console.log(res);
                // console.log(res.data.object);
                // this.lis = res.data.object
                this.regionList = res.data.data

            })
        },


        // clicked1(item) {

        //     this.query = {
        //         typeId: item
        //     }

        //     console.log(this.query)
        // },
        // clicked2(item) {

        //     this.query = {
        //         region: item
        //     }
        //     console.log(this.query)
        // },
        // clicked3(item) {


        //     this.query = {
        //         year: item
        //     }
        //     console.log(this.query)
        // }
    },
    created() {
        this.query.typeId = this.$route.query.typeId;
        if (this.query.typeId) {
            //再查询一次
            this.getQuery();
        }
        else {
            this.query.typeId = 1;
            this.getQuery();
        }

    },
    mounted() {
        this.getList();
        this.getRegionList();
        this.getTimeList();




    },
    computed: {
        showtime: () => {

            return (time) => {

                return moment((new Date(time))).format('Y年M月DD日')
            }
        }
    }
}
</script>
<style scoped>
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

.Types input:checked+label {
    background-color: rgb(0, 149, 246);
    color: #fff;
    border-radius: 2px;
}







.subnav {
    width: 100%;
    height: 60px;
    display: flex;
    justify-content: center;
    background-color: #47464A;
}

.subnav div {
    width: 144px;
    height: 100%;
    font-size: 16px;
    color: #999;
    line-height: 60px;
}

.tag {
    margin: auto;
    width: 1120px;
    margin-top: 40px;
    border: 1px solid #e5e5e5;
    padding: 0 20px;
}








.films-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    width: 1200px;
    margin: auto;
}


.film-item {
    width: 160px;
    margin-top: 30px;
    margin-left: 30px;
    text-align: center;
}

.film-item-hover {
    width: 218px;
    height: 300px;
    display: flex;
    flex-direction: column;
}

.film-item-hover-info div {
    display: flex;
}

.films-list-img {
    width: 160px;
    height: 220px;
}




.card {
    position: relative;
    width: 11.875em;
    height: 16.5em;
    box-shadow: 0px 1px 13px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 120ms;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #fff;
    padding: 0.5em;
    padding-bottom: 3.4em;
    margin: 15px;
}

.card::after {
    content: "去购票！";
    padding-top: 1.25em;
    padding-left: 1.25em;
    position: absolute;
    left: 0;
    bottom: -10px;
    background: #00AC7C;
    color: #fff;
    height: 2.5em;
    width: 90%;
    transition: all 80ms;
    font-weight: 600;
    text-transform: uppercase;
    opacity: 0;
}

.card .title {
    font-family: Arial, Helvetica, sans-serif;
    font-size: 0.9em;
    position: absolute;
    left: 0.625em;
    bottom: 1.875em;
    font-weight: 400;
    color: #000;
}

.card .price {
    margin-top: 5px;
    font-family: Impact, Haettenschweiler, 'Arial Narrow Bold', sans-serif;
    font-size: 0.9em;
    position: absolute;
    left: 0.625em;
    bottom: 0.625em;
    color: #000;
}

.card:hover::after {
    bottom: 0;
    opacity: 1;
}

.card:active {
    transform: scale(0.98);
}

.card:active::after {
    content: "Added !";
    height: 3.125em;
}

.text {
    position: absolute;
    right: 15px;
    bottom: 10px;
    font-size: 30px;
    font-style: italic;
    color: #ffb400;
}

.image {
    background: rgb(241, 241, 241);
    width: 100%;
    height: 100%;
    display: grid;
    place-items: center;
    background-size: 190px 264px;
    position: relative;
}
</style>