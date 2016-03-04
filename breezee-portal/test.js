/**
 * Created by Shubert.Wang on 2016/1/15.
 */
var weChatUtil = require('./module/util/wechat.js');


//weChatUtil.getMenu(function (menu) {
//    console.log(menu);
//});

var menuData = require('./test.json');
weChatUtil.updateMenu(menuData, function (result) {
    console.log(result);
});