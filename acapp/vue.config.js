// role: 让最终打包的js和css文件不分块，以便于在服务器上部署时只需要引入一个js和一个css文件

const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    // No need for splitting
    optimization: {
      splitChunks: false
    }
  }
})