<template>
    <div v-for="d in data" style="display: flex;flex-direction: column;">
        <div style="margin-bottom: 16px;margin-top: 30px;">
            <div style="padding-left: 20px;">{{ d.role }} </div>
        </div>
        <ul style="list-style: none;padding-left: 0;margin: 0;width: 750px;">
            <li v-for="c, index in d.list" style="float: left;
                                                width: 128px;
                                                margin-left: 20px;
                                                margin-bottom: 20px;
                                                text-overflow: ellipsis;
                                                white-space: nowrap;
                                                ">
                <div>
                    <img :src="c.img" alt="" style="width: 128px;height: 170px;">

                </div>
                <div style="margin-top: 5px;display: flex;justify-content: center;align-items: center;">
                    <div>{{ c.name }}</div>
                </div>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    data() {
        return {
            data: [{
                role: '导演',
                list: []
            }]
        }
    },
    methods:{
        getActor(){
            this.$axios({
                method:'get',
                url:'/app/movie-people/get/'+this.$route.query.id,
            }).then(e=>{
                let i=0;
                e.data.data.forEach(element => {
                    this.data.forEach(j=>{
                        if (j.role==element.job) {
                            j.list.push(element)
                            i=i+1;
                        }
                    })
                    if (i==0) {
                        this.data.push({role:element.job,list:[element,]})
                    }
                    i=0
                });
                console.log("=======",e);
            })
        }
    },
    mounted(){
        this.getActor()
    }
}
</script>