module.exports = {
    devServer: {
        proxy: {
            'api': {
                target: 'http://localhost:14580',
                changeOrigin: true,
                secure: false
            },
            'images': {
                target: 'http://localhost:14580',
                changeOrigin: true,
                secure: false
            }
        }
    }
}