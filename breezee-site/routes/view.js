var express = require('express');
var extend = require('extend');
var router = express.Router();

var viewRoutes = {
    _getClientIp: function (req) {
        return req.headers['x-forwarded-for'] ||
            req.connection.remoteAddress ||
            req.socket.remoteAddress ||
            req.connection.socket.remoteAddress;
    },

    /**
     * 页面渲染
     * @param url
     * @param req
     * @param res
     */
    _render_: function (url, req, res) {
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
        extend(queryData, req.session.userData);
        var routerPath = url.split('?')[0].split('/');
        var fun = _this;
        for (var i = 2; i < routerPath.length; i++) {
            fun = fun[routerPath[i]];
            if (fun == null) {
                break;
            }
        }
        if (fun == null) {
            res.render(url.substring(1), rendParam);
        } else {
            fun(queryData, res, function (body) {
                rendParam.body = body;
                res.render(url.substring(1), extend({body: body}, rendParam))
            });
        }
    }
};
viewRoutes.account = require('./viewRoutes/account.js');
viewRoutes.message = require('./viewRoutes/message.js');
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
            //TODO: 全局缓存token
            global.weChatUtil.getOpenId(req.query.code, function (openId) {
                if (openId) {   //如果可以获取到openId的话，则获取用户信息
                    req.session.openId = openId;
                    myUtil.customerInfo(global.config.service['crm'] + '/user/code/' + openId, function (userData) {
                        req.session.userData = userData;
                        req.session.userData.remoteIp = viewRoutes._getClientIp(req);
                        req.session.userData.openId = openId;
                        viewRoutes._render_(url, req, res);
                    });
                    //我们在获取用户成功后获取token
                    global.weChatUtil.validateToken();
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
                viewRoutes._render_(url, req, res);
            }
        }
    } catch (e) {
        console.error('请求' + url + '报错');
        console.error(e);
        throw e;
    }
});

module.exports = router;
