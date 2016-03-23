var express = require('express');
var router = express.Router();
var xml2json = require('basic-xml2json');

router.get('/', function (req, res, next) {

    var queryData = req.query;
    console.log("===get=======");
    console.log(queryData);
    console.log("==========");
    //res.send(queryData.echostr);
    res.send("success");

    /* url = req.originalUrl;

     if(url.indexOf("?") >= 0){
     url = url.substring(0, url.indexOf("?")).replace('/verifyToken','');
     }
     endType = /mobile/.test(req.headers['user-agent'])?"mobile":"desktop";

     res.render('login', {
     path : url,
     endType : "",
     title : '走在nodeJs的路上',
     redirect : ""
     });*/
});

router.post('/', function (req, res, next) {

    var queryData = req.query, bodyData = req.body;
    console.log("====post======");
    console.log(queryData);
    console.log(bodyData);
    console.log("==========");
    var words = {
        a: '欢迎关注“索迪斯服务”,我们将竭诚为您服务，提高您的生活质量！\n点击下方导航菜单查看精彩内容，更多线上服务，敬请期待！',
        b: '感谢来信！\n如果您对我们的服务有任何意见或建议，欢迎留言，我们将尽快回复您！',
        c: '您好！很高兴为您服务！',
        d: '点击下方菜单“最新动态”→“关于索记”，欲知更多详情，欢迎登陆索迪斯中国官网：www.sodexo.cn，谢谢！',
        e: '点击下方菜单“最新动态”→“健康又美丽”获取健康营养小贴士，健康美丽不用愁！',
        f: '正在烦恼吃什么？\n点击下方菜单“最新动态”→“当季好味道/推荐菜单”',
        g: '点击下方菜单“订购服务”查看，如有任何疑问，请联系员工餐厅负责人，也欢迎您通过微信平台留言，谢谢！\n更多线上服务，敬请期待！',
        h: '欢迎联系员工餐厅负责人，也欢迎您通过微信平台留言，谢谢！'
    };

    var retKey = function (content) {
        var ret = 'b';
        if(/Hello|hello|Hi|hi|你好|早上好|中午好|[微笑]/.test(content))
            ret = 'c';
        else if (/索迪斯/.test(content))
            ret = 'd';
        else if (/健康|美丽/.test(content))
            ret = 'e';
        else if (/早餐|午餐|晚餐|推荐|菜单|吃/.test(content))
            ret = 'f';
        else if (/订位|服务/.test(content))
            ret = 'g';
        else if (/电话|联系方式/.test(content))
            ret = 'h';
        return ret;
    }
    var retStr = [];
    if (bodyData.xml) {
        var xml = bodyData.xml;
        //{ xml:
        //{ tousername: [ 'gh_c599ecf3822b' ],
        //    fromusername: [ 'opkN1t-njzuumf7t2d3Xlw8sUg2U' ],
        //    createtime: [ '1458704717' ],
        //    msgtype: [ 'text' ],
        //    content: [ 'hello' ],
        //    msgid: [ '6265089054441838449' ] } }
        var content = words['b'];
        if(xml.msgtype=='event'){
            content = words['a'];
        } else if(xml.msgtype=='text'){
            var k = retKey(xml.content[0]);
            content = words[k];
        }
        retStr.push("<xml>");
        retStr.push("<ToUserName>" + xml.fromusername[0] + "</ToUserName>");
        retStr.push("<FromUserName>" + xml.tousername[0] + "</FromUserName>");
        retStr.push("<CreateTime>" + xml.createtime[0] + "</CreateTime>");
        retStr.push("<MsgType>text</MsgType>");
        retStr.push("<Content>"+content+"</Content>");
        retStr.push("</xml>")
    }
    //res.send(queryData.echostr);
    res.send(retStr.join(""));
});

module.exports = router;
