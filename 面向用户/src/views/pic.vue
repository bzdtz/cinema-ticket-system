<template>
    <div>

        <ul style="
                    width: 750px;
                    list-style: none;
                    padding-left: 0;
                    margin: 0;
                    margin-left: -20px;">
            <li v-for="p, index in pic" style="float: left;
                                        margin-top: 20px;
                                        margin-left: 20px;">


                <el-image style=" cursor: pointer;width: 104px;height: 104px;" :src="p.img" :zoom-rate="1.2" :max-scale="7"
                    :min-scale="0.2" :preview-src-list="picp" :initial-index="index" fit="cover" />

            </li>
        </ul>

    </div>
</template>
<script>
export default {
    data() {
        return {
            pic: ["11b6e79f38f35c8a117caf0b48e8550b.jpg", "2.jpg", "3.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg",
                "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg",
                "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg",
                "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg",
                "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg",
                "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg", "11b6e79f38f35c8a117caf0b48e8550b.jpg",]
            , picp: ["1-p.jpg", "2-p.jpg", "3-p.jpg"]
        }
    },
    methods: {
        getPic() {
            this.$axios({
                method: 'get',
                url: '/app/movie-img/imgs/' + this.$route.query.id,
            }).then((result) => {
                console.log(result.data.data);
                this.pic = result.data.data
                this.picp = []
                result.data.data.forEach(element => {
                    this.picp.push(element.img)
                });
            }).catch((err) => {

            });
        }
    },
    mounted() {
        this.getPic()
    }
}
</script>