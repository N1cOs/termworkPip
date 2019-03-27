module.exports = {
    publicPath: '/uniss',
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:14580',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': '/uniss/api/'
                },
                secure: false
            }
        }
    }
}