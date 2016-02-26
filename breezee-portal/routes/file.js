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
    form.uploadDir = global.config.upload.uploadDir;	 //设置上传目录
    form.keepExtensions = true;	 //保留后缀
    form.maxFieldsSize = 2 * 1024 * 1024;   //文件大小

    var returnData = [];

    form.parse(req, function(err, fields, files) {
        if (err) {
            console.log('error')
        }
        console.log(++count);
        var avatarName = uuid.v1();
        var newPath = form.uploadDir + avatarName;

        console.log(files);
        console.log("===========================================================================");
        console.log(fields);
        fs.renameSync(files.file.path, newPath);  //重命名
        res.send({success : true, fileId : avatarName});
    });
});

module.exports = router;
