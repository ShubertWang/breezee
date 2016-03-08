/**
 * Created by Shubert.Wang on 2016/1/27.
 */
Dolphin.defaults.mockFlag = true;
$(function () {
    menu.select('index');

    var page = {
        pagination : null
    };

    page.init = function(){
        this.initCount();
        this.initPagination();
        this.renderMessage();
    };

    page.initCount = function () {
        Dolphin.ajax({
            url : '/data/index',
            async : false,
            onSuccess : function (reData) {
                for(var key in reData.value){
                    $('#' + key).html(reData.value[key]);
                }
            }
        });
    };
    page.initPagination = function () {
        var _this = this;
        this.pagination = new Dolphin.PAGINATION({
            panel : '#pagination',
            pageSize : 20,
            pageNumber : 1,
            pageSizeOption : [20, 50, 100],
            onChange : function () {
                _this.renderMessage(this.opts.pageSize, this.opts.pageNumber);
            }
        });
    };
    page.renderMessage = function (pageSize, pageNumber) {
        var _this = this;
        Dolphin.ajax({
            url : '/data/indexMessage',
            data : {
                pageSize : pageSize,
                pageNumber : pageNumber
            },
            loading : true,
            onSuccess : function (reData) {
                var panel = $('#messagePanel').empty();
                for(var i = 0; i < reData.rows.length; i++){
                    var link = $('<a href="javascript:alert(\''+reData.rows[i].id+'\')" class="list-group-item">').appendTo(panel);
                    $('<span class="date">').html(Dolphin.date2string(new Date(reData.rows[i].createTime), "yyyy-MM-dd")).appendTo(link);
                    link.append(reData.rows[i].title);
                }
                _this.pagination.initData({
                    total : reData.total
                });
                _this.pagination.refresh();
            }
        });
    };

    page.init();
});
