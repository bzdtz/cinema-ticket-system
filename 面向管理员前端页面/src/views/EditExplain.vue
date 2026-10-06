<template>
     <el-button class="primary" @click="saveExpalin">确认保存</el-button>
     <div ref="editorElem" style="height: 500px;"></div> 
</template>

<script>
// import wangEditor from 'wangeditor'
    export default{
        name: 'EditExplain',
        data() {
            return {
                foodId: 0,
                editor:null, 
            }
        },
        methods: {
            saveExpalin(){
                if(this.foodId>0){
                    this.$axios({
                        method:'post',
                        url: '/food/explain',
                        data: {
                            foodId: this.foodId,
                            foodExplain: this.editor.txt.html()
                        }
                    }).then(res=>{
                        if(res.data.code == 200){
                            this.$message.success("保存成功");
                        }
                    })
                }
            },
            createEditor(){
                // 初始化编辑器
                this.editor = new wangEditor(this.$refs.editorElem);

                // 配置编辑器
                this.editor.config.uploadImgShowBase64 = true; // 显示Base64编码的图片
                this.editor.config.uploadImgMaxLength = 5 * 1024 * 1024; // 限制上传图片的大小为5MB

                // 创建编辑器
                this.editor.create();
            }
        },
        mounted(){
            this.createEditor();
            console.log('------------', this.$route.query.foodId)
            this.foodId = this.$route.query.foodId;
            //根据餐品id查询详情
            this.$axios({
                method: 'get',
                url: '/food/explain/'+this.foodId
            }).then(res=>{
                console.log(res.data.data);
                this.editor.txt.html(res.data.data);//在编辑器中显示html内容
            })
        }
        
    } 
</script>

<style>
</style>