/**
 * Created by Shubert.Wang on 2016/1/15.
 */

var i18n = require('i18n');

i18n.configure({
    // setup some locales - other locales default to en silently
    locales: ['en','zh'],
    directory: __dirname + '/config/locales'
});

i18n.setLocale('en');
console.log(i18n.__('test'));