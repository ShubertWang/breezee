var express = require('express');
var router = express.Router();
var viewRoutes = {};
viewRoutes.account = require('./viewRoutes/account.js');
viewRoutes.message = require('./viewRoutes/message.js');
viewRoutes.restaurant = require('./viewRoutes/restaurant.js');
viewRoutes.order = require('./viewRoutes/order.js');

/* GET users listing. */
router.get('*', function(req, res, next) {
    try{
        var url, endType, userInfo,
            queryData;

        userInfo = true;
        //userInfo = req.session.username;
        //endType = /mobile|Mobile/.test(req.headers['user-agent'])?"/mobile":"/desktop";
        endType = "/mobile";

        if(userInfo == null){
            res.render(endType.substring(1) + '/login', {
                path : endType + '/login',
                endType : "",
                title : '登录',
                redirect : req.url,
                data : {},
                body : {}
            });
        }else{
            url = endType + req.url;
            queryData = req.query;

            if(url.indexOf("?") >= 0){
                url = url.substring(0, url.indexOf("?"));
            }

            var fun = viewRoutes;
            var routerPath = req.url.split('?')[0].split('/');
            for(var i = 1; i < routerPath.length; i++){
                fun = fun[routerPath[i]];
                if(fun == null){
                    break;
                }
            }
            if(fun == null){
                res.render(url.substring(1), {
                    path : url,
                    data : queryData,
                    body : {},
                    session:req.session,
                    cookie : req.cookies
                });
            }else{
                fun(queryData, res, function(body){
                    res.render(url.substring(1), {
                        path : url,
                        data : queryData,
                        body : body,
                        session:req.session,
                        cookie : req.cookies
                    });
                })
            }
        }
    }catch(e){
        console.error('请求' + url + '报错');
        console.error(e);
        throw e;
    }
});

module.exports = router;
