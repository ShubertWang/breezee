//fis-conf.js
fis.match('/dolphin/js/*.js', {
  packTo: '/dolphin/dolphin.js'
});
fis.media('core').match('{/dolphin/js/core.js,/dolphin/js/validate.js,/dolphin/js/form.js,/dolphin/js/enum.js}', {
  packTo: '/dolphin/dolphin.core.js'
});

fis.media('prod').match('/js/*.js', {
  optimizer: fis.plugin('uglify-js')
});
fis.media('prod').match('/dolphin/js/*.js', {
  packTo: '/dolphin/dolphin.min.js'
});
fis.media('prod').match('{/dolphin/js/core.js,/dolphin/js/validate.js,/dolphin/js/form.js,/dolphin/js/enum.js}', {
    packTo: '/dolphin/dolphin.core.min.js'
});

fis.match('/dolphin/js/core.js', {
    packOrder: -100
});
fis.match('/dolphin/js/i18n.js', {
    packOrder: -90
});
fis.match('/dolphin/js/formI18nBox.js', {
    packOrder: -85
});
fis.match('/dolphin/js/formFileBox.js', {
    packOrder: -83
});
fis.match('/dolphin/js/validate.js', {
    packOrder: -80
});
fis.match('/dolphin/js/form.js', {
    packOrder: -70
});
fis.match('/dolphin/js/enum.js', {
    packOrder: -60
});
fis.match('/dolphin/js/pagination.js', {
    packOrder: -50
});
fis.match('/dolphin/js/list.js', {
    packOrder: -40
});
fis.match('/dolphin/js/tree.js', {
    packOrder: -30
});
fis.match('/dolphin/js/horizontalTree.js', {
    packOrder: -20
});
fis.match('/dolphin/js/grid.js', {
    packOrder: -15
});
fis.match('/dolphin/js/templateGrid.js', {
    packOrder: -10
});
fis.match('/dolphin/js/refWin.js', {
    packOrder: -5
});

//编译less
fis.match('/dolphin/less/dolphin.less',{
  release : '/dolphin/css/dolphin.css',
  parser: fis.plugin('less'),
  rExt: '.css'
});
fis.media('prod').match('/dolphin/less/dolphin.less',{
  release : '/dolphin/css/dolphin.min.css',
  parser: fis.plugin('less'),
  rExt: '.css'
});
fis.media('prod').match('/dolphin/less/dolphin.less',{
    optimizer: fis.plugin('clean-css')
});
fis.media('prod').match('/custom/css/*.css',{
    optimizer: fis.plugin('clean-css')
});

fis.set('project.ignore', [
  'node_modules/**',
  'output/**',
  'fis-conf.js'
]);

//发布地址
fis.match('*', {
  deploy: [fis.plugin('local-deliver', {
    to: ["../public"]
  })]
});
