const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port : 3000,
    proxy : {
      '/api/*' : {
        target: 'http://localhost:8080'
      },
    }
  },
  css: {
    loaderOptions: {
      sass: {
        data: `
          @import "@/assets/css/global.scss";
        `
      }
    }
  },
  configureWebpack: {
    entry: {
      app: './src/main.ts',
      style: [
        'bootstrap/dist/css/bootstrap.min.css',
        'admin-lte/dist/css/adminlte.css',
        'admin-lte/plugins/fontawesome-free/css/all.min.css'
      ],
      script: [
        'jquery/dist/jquery.min.js',
        'admin-lte/dist/js/adminlte.js',
        'admin-lte/dist/js/demo.js',
        'admin-lte/plugins/bootstrap/js/bootstrap.bundle.min.js',
      ]
    }
  }
})
