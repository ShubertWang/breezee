var express = require('express');
var router = express.Router();

var viewRoutes = {

    /**
     * 页面渲染
     * @param url
     * @param req
     * @param res
     */
    render: function (url, req, res) {
        var _this = this, fun,
            queryData = req.query,
            rendParam = {
                path: url,
                data: queryData,
                body: {},
                session: req.session,
                userData: req.session.userData || {},
                cookie: req.cookies
            };
        var routerPath = url.split('?')[0].split('/');
        for (var i = 1; i < routerPath.length; i++) {
            fun = fun[routerPath[i]];
            if (fun == null) {
                break;
            }
        }
        if (fun == null) {
            res.render(url.substring(1), rendParam);
        } else {
            fun(queryData, res, function (body) {
                res.render(url.substring(1), extend({body: body}, rendParam))
            });
        }
    }
};
viewRoutes.account = require('./viewRoutes/account.js');
viewRoutes.message = require('./viewRoutes/message.js');
viewRoutes.restaurant = require('./viewRoutes/restaurant.js');
viewRoutes.order = require('./viewRoutes/order.js');

/**
 * 路由转发
 */
router.get('*', function (req, res, next) {
    try {
        var url, endType, userInfo,
        //userInfo = true;
            userInfo = req.session.openId;
        //endType = /mobile|Mobile/.test(req.headers['user-agent'])?"/mobile":"/desktop";
        endType = "/mobile";
        url = endType + req.url;
        if (global.config.production) { //如果是线上环境
            global.weChatUtil.getOpenId(req.query.code, function (openId) {
                if (openId) {   //如果可以获取到openId的话，则获取用户信息
                    req.session.openId = openId;
                    myUtil.customerInfo(global.config.service['crm'] + '/user/code/' + openId, function (userData) {
                        req.session.userData = userData;
                        viewRoutes.render(url, req, res);
                    });
                } else {
                    res.send({success: false, msg: "无法获取您的OpenId，请确认是在微信菜单中打开本网页！"});
                }
            });
        } else {
            if (userInfo == null) {
                res.render(endType.substring(1) + '/login', {
                    path: endType + '/login',
                    endType: "",
                    title: '登录',
                    redirect: req.url,
                    data: {},
                    body: {},
                    userData: {}
                });
            } else {
                if (url.indexOf("?") >= 0) {
                    url = url.substring(0, url.indexOf("?"));
                }
                viewRoutes.render(url, req, res);
            }
        }
    } catch (e) {
        console.error('请求' + url + '报错');
        console.error(e);
        throw e;
    }
});

module.exports = router;
