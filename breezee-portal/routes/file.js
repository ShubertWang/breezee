var express = require('express');
var router = express.Router();

var formidable = require('formidable');
var fs = require('fs');
var uuid = require('uuid');

/* GET home page. */
router.post('/', function(req, res, next) {
    console.log('upload start');
    var form = new formidable.IncomingForm();   //创建上传表单
    form.encoding = 'utf-8';		//设置编辑
    form.uploadDir = "D:\\mywork\\workspace0\\breezee\\breezee-portal\\public\\uploadFiles\\images\\";	 //设置上传目录
    //form.uploadDir = "\/home\/sodexho\/media\/breezee\/images\/";
    form.keepExtensions = true;	 //保留后缀
    form.maxFieldsSize = 2 * 1024 * 1024;   //文件大小

    var returnData = [];

    form.parse(req, function(err, fields, files) {
        if (err) {
            console.log('error')
        }
        //var avatarName = uuid.v1();
        //var newPath = form.uploadDir + avatarName;
        var fileName = files.upfile.path.replace(/D:\\mywork\\workspace0\\breezee\\breezee-portal\\public\\uploadFiles\\images\\/g,'');
        console.log(files);
        console.log("===========================================================================");
        console.log(fields);
        //fs.renameSync(files.upfile.path, newPath);  //重命名
        var result = "{\"name\":\""+ fileName +"\", \"originalName\": \""+ fileName +"\", \"size\": "+ files.upfile.size +", \"state\": \"SUCCESS\", \"type\": \""+ files.upfile.type +"\", \"url\": \"/uploadFiles/images/"+fileName+"\"}";
        //var result = {
        //    name:fileName,
        //    originalName:fileName,
        //    size:files.upfile.size,
        //    state:"SUCCESS",
        //    type:files.upfile.type,
        //    url:'/uploadFiles/images/icon_tx.png'
        //};
        res.set('Content-Type', 'text/html;charset=utf-8');
        res.send(result);
    });
});

module.exports = router;
