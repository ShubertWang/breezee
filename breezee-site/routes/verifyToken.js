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

        retStr.push("<xml>");
        retStr.push("<ToUserName>"+xml.fromusername[0]+"</ToUserName>");
        retStr.push("<FromUserName>"+xml.tousername[0]+"</FromUserName>");
        retStr.push("<CreateTime>"+xml.createtime[0]+"</CreateTime>");
        retStr.push("<MsgType>"+xml.msgtype[0]+"</MsgType>");
        retStr.push("<Content>你得请求已经收到，我们正在处理中</Content>");
        retStr.push("</xml>")
        console.log(retStr.join(""));
    }
    //res.send(queryData.echostr);
    res.send(retStr.join(""));

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

module.exports = router;
