var path = require('path');
const HtmlWebPackPlugin = require("html-webpack-plugin");

module.exports = {
    entry: './src/app.js',
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './dist/bundle.js'
    },
    devServer: {
        contentBase: path.join(__dirname, 'dist'),
        hot: true,
        port: 3000,
        historyApiFallback: true
    
    },
    module: {
        rules: [{
            test: path.join(__dirname, '.'),
            exclude: /(node_modules)/,
            use: [{
                loader: 'babel-loader',
                options: {
                    presets: ["@babel/preset-env", "@babel/preset-react"]
                }
            }]
        }, {
            test: /\.css$/,
            loader: "style-loader!css-loader"
        }, {
            test: /\.html$/,
            use: [{
                loader: "html-loader"
            }]
        }]
    },
    plugins: [
        new HtmlWebPackPlugin({
            template: "./src/index.html",
            filename: "./dist/index.html"
        })
    ]
};