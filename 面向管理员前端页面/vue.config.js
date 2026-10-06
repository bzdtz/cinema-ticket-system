const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  lintOnSave: 'warning',
  transpileDependencies: true,
  devServer: {
    port: 8081   //修改启动端口

  }

})
