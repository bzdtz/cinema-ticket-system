<template>
    <div>
        <div style="background-image: url('./banner_bg.png'); height: 376px; margin-bottom: 75px;"
             v-if="hasMovie == true">
            <div style="width: 1200px;height: 100%;margin: auto;display: flex;">
                <div style="margin: 0 30px; position: relative;top: 60px;    border: 4px solid #fff;height: 330px;">
                    <img :src="movie.banner" alt="" width="240" height="330">
                </div>
                <div style="margin: 70px 30px 0 30px;">
                    <div style="color: #fff;">
                        <h1 class="name">{{ movie.name }}</h1>
                        <div v-for="i in movieType" style="display: flex;">
                            <p class="hoverPointer"  @click.prevent="navigateToFilm(i.movietype.id)" :key="i.movietype.id" style="display: flex;margin-top: 10px;">{{i.movietype.typename}}</p>
                        </div>
                        <!-- <div style="margin-top: 10px;display: flex;">
                            <p v-for=" i in movie.tag" style="padding-right: 10px;">{{ i }}</p>
                        </div> -->
                        <div style="display: flex;margin-top: 5px;">
                            <p style="padding-right: 5px;">{{ movie.region }}</p> / <p style="padding-left: 5px;">{{
                            movie.movieLength }}分钟</p>
                        </div>

                        <p style="margin-top: 5px;">{{ movie.releaseTime }} 中国大陆上映</p>
                        <p style="margin-top: 5px;"></p>

                    </div>
                    <div style="display: flex;position: relative;top: 45px;width: 500px;justify-content: space-between;">
                        <div style="display: flex;flex-direction: column;justify-content: space-between;">
                            <div style="width: 300px;margin-top: 5px;">
                                <el-button color="#756189" size="large" style="padding: 5px 40px;"
                                           @click="wantAdd">{{ want ? '已想看' : '想看' }}
                                </el-button>
                                <el-button color="#756189" size="large" style="padding: 5px 40px;"
                                           @click="openReview">评分
                                </el-button>
                                <p style="font-size: 12px;margin: 8px 0 0;color: #fff;opacity: .75;">
                                    {{ wantNumber }} 人想看
                                </p>
                            </div>
                            <div>
                                <el-button type="danger" color="#DF2D2D" size="large" @click="toCinema"
                                           style="padding: 5px 90px;">特惠购票
                                </el-button>

                            </div>
                        </div>
                        <div style="color: #fff;">
                            <div>
                                <p style="font-size: 13px;">评分</p>
                                <div>
                                    <div>
                                        <el-rate v-model="score" disabled show-score text-color="#ff9900"
                                                 score-template="{value} 分" :max="5"/>
                                    </div>
                                </div>

                            </div>
                            <div>
                                <p style="font-size: 13px;">累计票房</p>
                                <div>
                                    <p style="font-size: 30px;">{{ movie.boxOffice/10000}}万</p>
                                </div>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>


        <div class="main-movie">
            <div class="left">
                <el-breadcrumb :separator-icon="ArrowRight">
                    <el-breadcrumb-item :to="{ path: '/' }">码上影院</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/films' }">电影</el-breadcrumb-item>
                    <el-breadcrumb-item>{{ movie.name }}</el-breadcrumb-item>

                </el-breadcrumb>
                <div>
                    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                             router>
                        <el-menu-item index="/introduced" :route="`/introduced?id=`+this.$route.query.id">介绍
                        </el-menu-item>
                        <el-menu-item index="/actor" :route="`/actor?id=`+this.$route.query.id">演职人员</el-menu-item>
                        <el-menu-item index="/pic" :route="`/pic?id=`+this.$route.query.id">图集</el-menu-item>
                    </el-menu>
                </div>
                <router-view style="width: 750px;"></router-view>

                <div class="review-box">
                    <div style="display: flex;justify-content: space-between;align-items: center;margin: 30px 0 10px;">
                        <p class="int-title" style="font-size: 18px;">
                            影片评论 <span style="font-size: 12px;color: #999;">({{ commentTotal }})</span>
                        </p>
                        <el-button size="small" @click="openReview">写评论</el-button>
                    </div>
                    <div v-if="comments.length == 0" style="color: #999;font-size: 13px;padding: 20px 0;">
                        还没有人评论，来发表第一条吧
                    </div>
                    <div v-for="c in comments" :key="c.id" class="review-item">
                        <div style="display: flex;justify-content: space-between;align-items: center;">
                            <div style="display: flex;align-items: center;">
                                <el-avatar :size="32" :src="c.user ? c.user.headImg : ''">
                                    {{ c.user && c.user.userName ? c.user.userName.charAt(0) : '影' }}
                                </el-avatar>
                                <div style="margin-left: 10px;">
                                    <p style="font-size: 14px;margin: 0;">{{ c.user ? c.user.userName : '匿名用户' }}</p>
                                    <p style="font-size: 12px;color: #999;margin: 0;">{{ c.createtime }}</p>
                                </div>
                            </div>
                            <div style="display: flex;align-items: center;">
                                <el-rate :model-value="c.score / 2" disabled size="small"/>
                                <span style="font-size: 12px;color: #ff9900;margin-left: 6px;">{{ c.score }}分</span>
                                <el-button v-if="c.userId == myUserId" link type="danger" size="small"
                                           @click="removeComment(c)">删除
                                </el-button>
                            </div>
                        </div>
                        <p style="font-size: 14px;margin: 8px 0 0;line-height: 22px;">{{ c.content }}</p>
                    </div>
                    <el-pagination v-if="commentTotal > commentSize" background layout="prev, pager, next"
                                   :total="commentTotal" :page-size="commentSize"
                                   :current-page="commentCurrent" @current-change="changeCommentPage"
                                   style="margin-top: 15px;"/>
                </div>
            </div>
            <div class="right" style="padding-left: 35px;">
                <div style="margin-top: 30px;margin-bottom: 20px;">
                    <p class="int-title" style="font-size: 18px;">预告片</p>
                </div>
                <div style="display: flex;margin-bottom: 20px;">
                    <div style="background-image: url('shuke1.jpg');width: 120px;height: 68px;  background-size: 120px 68px;">
                        <i
                                style="background-color: tomato;color: #FFF;padding: 0 5px;position: relative;bottom: -47px;">1</i>
                    </div>
                    <div style="padding-top: 15px;">
                        <p>
                            《舒克贝塔·五角飞碟》预售开启！电影《舒克贝塔·五角飞碟》 “跨时空冒险”预告
                        </p>
                        <p style="font-size: 12px; padding-top: 5px;">
                            <svg t="1703582206156" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                 xmlns="http://www.w3.org/2000/svg" p-id="9907" width="10" height="10">
                                <path
                                        d="M852.5 533.9L279 864.7c-11.9 6.9-27.2 2.8-34.1-9.1-2.2-3.8-3.3-8.1-3.3-12.5V181.5c0-13.8 11.2-24.9 24.9-24.9 4.4 0 8.7 1.2 12.5 3.3l573.4 330.8c11.9 6.9 16 22.1 9.1 34.1-2.1 3.8-5.2 6.9-9 9.1z"
                                        p-id="9908"></path>
                            </svg>
                            99.4万
                        </p>
                    </div>
                </div>
                <div style="display: flex;margin-bottom: 20px;">
                    <div style="background-image: url('shuke2.jpg');width: 120px;height: 68px;  background-size: 120px 68px;">
                        <i
                                style="background-color: tomato;color: #FFF;padding: 0 5px;position: relative;bottom: -47px;">2</i>
                    </div>
                    <div style="padding-top: 15px;">
                        <p>
                            《舒克贝塔·五角飞碟》预售开启！电影《舒克贝塔·五角飞碟》 “跨时空冒险”预告
                        </p>
                        <p style="font-size: 12px; padding-top: 5px;">
                            <svg t="1703582206156" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                 xmlns="http://www.w3.org/2000/svg" p-id="9907" width="10" height="10">
                                <path
                                        d="M852.5 533.9L279 864.7c-11.9 6.9-27.2 2.8-34.1-9.1-2.2-3.8-3.3-8.1-3.3-12.5V181.5c0-13.8 11.2-24.9 24.9-24.9 4.4 0 8.7 1.2 12.5 3.3l573.4 330.8c11.9 6.9 16 22.1 9.1 34.1-2.1 3.8-5.2 6.9-9 9.1z"
                                        p-id="9908"></path>
                            </svg>
                            100.9万
                        </p>
                    </div>
                </div>
                <div style="display: flex;margin-bottom: 20px;">
                    <div style="background-image: url('shuke3.jpg');width: 120px;height: 68px;  background-size: 120px 68px;">
                        <i
                                style="background-color: tomato;color: #FFF;padding: 0 5px;position: relative;bottom: -47px;">3</i>
                    </div>
                    <div style="padding-top: 15px;">
                        <p>
                            《舒克贝塔·五角飞碟》《舒克贝塔·五角飞碟》曝“鼠来好运”版预告 童年回忆席卷而来！
                        </p>
                        <p style="font-size: 12px; padding-top: 5px;">
                            <svg t="1703582206156" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                 xmlns="http://www.w3.org/2000/svg" p-id="9907" width="10" height="10">
                                <path
                                        d="M852.5 533.9L279 864.7c-11.9 6.9-27.2 2.8-34.1-9.1-2.2-3.8-3.3-8.1-3.3-12.5V181.5c0-13.8 11.2-24.9 24.9-24.9 4.4 0 8.7 1.2 12.5 3.3l573.4 330.8c11.9 6.9 16 22.1 9.1 34.1-2.1 3.8-5.2 6.9-9 9.1z"
                                        p-id="9908"></path>
                            </svg>
                            100.9万
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <el-dialog title="发表影评" v-model="dialogTableVisible" width="520px">
            <div style="display: flex;align-items: center;margin-bottom: 15px;">
                <span style="width: 60px;">评分</span>
                <el-rate v-model="reviewRate" allow-half show-score text-color="#ff9900"
                         score-template="{value} 分" :max="5"/>
            </div>
            <el-input v-model="reviewContent" type="textarea" :rows="5" maxlength="1000" show-word-limit
                      placeholder="说说你对这部影片的看法"></el-input>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogTableVisible = false">取 消</el-button>
                    <el-button type="primary" :loading="reviewSaving" @click="submitReview">发 表</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                movieType:[],
                movieId:null,
                hasMovie: true,
                movie:
                    {
                        name: '',
                        tag: '',
                        region: '',
                        movie_length: '',
                        score: 9.6 / 2,
                        box_office: "",
                        synopsis: '',
                        langue: "",

                    },
                score: 0,
                want: false,
                wantNumber: 0,
                myUserId: null,
                dialogTableVisible: false,
                reviewRate: 0,
                reviewContent: '',
                reviewSaving: false,
                comments: [],
                commentTotal: 0,
                commentCurrent: 1,
                commentSize: 5

            }
        },
        methods: {
            navigateToFilm(id){
                this.$router.push({
                    path: 'films',
                    query: { typeId: id } });
            },
            getMovieType() {
                this.$axios({
                    method:'get',
                    url:"/app/movie/type/"+this.movieId
                }).then((res)=>{
                    console.log("这是当前电影所拥有的类型")
                    console.log(res);
                    if (res.data.code=200)
                    {
                        this.movieType=res.data.data;
                    }
                })
            },
            toCinema(){
              this.$router.push({
                  path:'/cinemas',
                  query:{
                      id:this.movieId
                  }

              })
            },
            getMovie() {
                this.$axios({
                    method: 'get',
                    url: '/app/movie/' + this.movieId,
                }).then((result) => {
                    this.movie = result.data.data
                    this.score = result.data.data.score / 2
                    this.wantNumber = result.data.data.wantNumber || 0
                }).catch((err) => {

                });
            },
            loadWant() {
                this.$axios({
                    method: 'get',
                    url: '/app/user-see-record/isWant/' + this.movieId
                }).then((res) => {
                    if (res.data.code == 200) {
                        this.want = res.data.data.want
                    }
                })
            },
            wantAdd() {
                this.$axios({
                    method: 'post',
                    url: '/app/user-see-record/toggle/' + this.movieId
                }).then((res) => {
                    if (res.data.code != 200) {
                        this.$message.error(res.data.msg)
                        return
                    }
                    this.want = res.data.data.want
                    this.wantNumber = res.data.data.wantNumber
                    this.$message.success(this.want ? '已加入想看' : '已取消想看')
                })
            },
            openReview() {
                this.dialogTableVisible = true
                this.$axios({
                    method: 'get',
                    url: '/app/movie-comment/mine/' + this.movieId
                }).then((res) => {
                    if (res.data.code == 200 && res.data.data) {
                        this.reviewRate = res.data.data.score / 2
                        this.reviewContent = res.data.data.content
                    }
                })
            },
            submitReview() {
                const content = (this.reviewContent || '').trim()
                if (!content) {
                    this.$message.error('请先写点内容')
                    return
                }
                if (!this.reviewRate) {
                    this.$message.error('请先打分')
                    return
                }
                this.reviewSaving = true
                this.$axios({
                    method: 'post',
                    url: '/app/movie-comment/add',
                    data: {
                        movieId: Number(this.movieId),
                        content: content,
                        score: this.reviewRate * 2
                    }
                }).then((res) => {
                    this.reviewSaving = false
                    if (res.data.code != 200) {
                        this.$message.error(res.data.msg)
                        return
                    }
                    this.$message.success('影评已发表')
                    this.dialogTableVisible = false
                    this.commentCurrent = 1
                    this.loadComments()
                    this.getMovie()
                }).catch(() => {
                    this.reviewSaving = false
                })
            },
            loadComments() {
                this.$axios({
                    method: 'get',
                    url: '/app/movie-comment/page/' + this.movieId,
                    params: {current: this.commentCurrent, size: this.commentSize}
                }).then((res) => {
                    if (res.data.code != 200) {
                        return
                    }
                    this.comments = res.data.data.data || []
                    this.commentTotal = res.data.data.totalRows || 0
                })
            },
            changeCommentPage(page) {
                this.commentCurrent = page
                this.loadComments()
            },
            removeComment(comment) {
                this.$confirm('确定删除这条影评吗？', '提示', {
                    type: 'warning',
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(() => {
                    this.$axios({
                        method: 'delete',
                        url: '/app/movie-comment/' + comment.id
                    }).then((res) => {
                        if (res.data.code != 200) {
                            this.$message.error(res.data.msg)
                            return
                        }
                        this.$message.success('已删除')
                        this.loadComments()
                        this.getMovie()
                    })
                }).catch(() => {
                })
            }
        }
        ,

        mounted() {
            //获取电影id
            this.movieId = this.$route.query.id;

            const loginUser = localStorage.getItem('loginUser');
            if (loginUser) {
                this.myUserId = JSON.parse(loginUser).id
            }

            this.getMovie();

            this.$axios({
                method: 'get',
                url: '/app/movie/type/' + this.movieId,
            }).then((result) => {
                this.movie.tag = []
                result.data.data.forEach(element => {
                    this.movie.tag.push(element.movietype.typename)

                });

                console.log(this.movie.tag);
            }).catch((err) => {

            });


            if (this.movieId){
                console.log("获取到当前的电影id了")
                console.log(this.movieId)

                this.getMovieType()
                this.loadWant()
                this.loadComments()

            }


        }
    }
</script>

<style scoped>
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

    .main-movie {
        width: 1200px;
        height: 100%;
        margin: auto;
        display: flex;
    }
    .hoverPointer:hover{
        cursor:pointer;
    }
    .review-box {
        width: 750px;
    }

    .review-item {
        border-top: 1px solid #eee;
        padding: 15px 0;
    }
</style>