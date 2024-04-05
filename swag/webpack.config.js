const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const outputPath = path.resolve(__dirname, 'dist');

module.exports = {
    output: {
        filename: '[name].bundle.js',
        path: outputPath,
    },
    mode: "development",
    module: {
        rules: [
            {
                test: /\.yaml$/,
                use: [
                    { loader: 'json-loader' },
                    { loader: 'yaml-loader', options:{ asJSON: true } }
                ]
            },
            {
                test: /\.s?[ac]ss$/i,
                use: [
                    "style-loader",
                    {
                        loader: "css-loader",
                        options: {
                            sourceMap: true,
                        },
                    },
                    {
                        loader: "sass-loader",
                        options: {
                            // Prefer `dart-sass`
                            implementation: require("sass"),
                            sourceMap: true,
                        },
                    },
                ],
            },
            {
                test: /\.(?:js|mjs|cjs)$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            ['@babel/preset-env', { targets: "defaults" }]
                        ]
                    }
                }
            }
        ],
    },plugins: [
        new CleanWebpackPlugin(),
        new CopyWebpackPlugin({patterns:[
                {
                    // Copy the Swagger OAuth2 redirect file to the project root;
                    // that file handles the OAuth2 redirect after authenticating the end-user.
                    from: require.resolve('swagger-ui/dist/oauth2-redirect.html'),
                    to: './'
                }
            ]}),
        new HtmlWebpackPlugin({
            template: 'public/index.html'
        })
    ],
};