var express = require('express');
var extend = require('extend');
var router = express.Router();

var viewRoutes = {

    _checkUser: {
        public: 1,
        site: 2,
        employee: 3
    },

    /**
     * 获取客户端的IP地址
     * @param req
     * @returns {*}
     * @private
     */
    _getClientIp: function (req) {
        var ip = req.headers['x-forwarded-for'] ||
            req.connection.remoteAddress ||
            req.socket.remoteAddress ||
            req.connection.socket.remoteAddress;
        //::ffff:127.0.0.1
        ip = ip || "127.0.0.1";
        var ind = ip.lastIndexOf(":");
        if (ind > -1)
            ip = ip.substring(ind + 1);
        return ip;
    },

    /**
     * 页面渲染
     * @param url
     * @param req
     * @param res
     */
    _render_: function (url, req, res) {
        if (url.indexOf("?") >= 0) {
            url = url.substring(0, url.indexOf("?"));
        }
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
        extend(true, queryData, req.session.userData, {remoteIp: _this._getClientIp(req)});
        var routerPath = url.split('/');
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
            fun(queryData, res, function (body, checkUser) {
                //1: public 2: site 3: employee
                if (checkUser && _this._checkUser[checkUser] < _this._checkUser[req.session.userData.userType]) {
                    url = '/mobile/noAccess';
                }
                rendParam.body = body;
                res.render(url.substring(1), extend({}, rendParam, {body: body}));
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
        url = endType + (req.url == '/'?"/index":req.url);
        if (userInfo == null) {
            if (global.config.production) {
                global.weChatUtil.getOpenId(req.query.code, function (openId) {
                    if (openId) {   //如果可以获取到openId的话，则获取用户信息
                        req.session.openId = openId;
                        myUtil.customerInfo(global.config.service['crm'] + '/user/code/' + openId, function (userData) {
                            req.session.userData = userData;
                            //我们在获取用户成功后获取token
                            global.weChatUtil.validateToken();
                            viewRoutes._render_(url, req, res);
                        });
                    } else {
                        res.send({success: false, msg: "无法获取您的OpenId，请确认是在微信菜单中打开本网页！"});
                    }
                });
            } else {
                res.render(endType.substring(1) + '/login', {
                    path: endType + '/login',
                    endType: "",
                    title: '登录',
                    redirect: req.url,
                    data: {},
                    body: {},
                    userData: {}
                });
            }
        } else {
            viewRoutes._render_(url, req, res);
        }
    } catch (e) {
        console.error('请求' + url + '报错');
        console.error(e);
        throw e;
    }
});

module.exports = router;
