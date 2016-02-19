Dolphin.defaults.mockFlag = true;
$(function () {
    var page = {};

    page.connect = {
        undoList : {
            url : '/data/workspace/undoList'
        }
    };

    page.init = function () {
        this.initPage();
        this.initEvent();

        return this;
    };

    page.initPage = function () {
        var _this = this;
        this.undoList = new Dolphin.LIST({
            panel : '#list',
            url : _this.connect.undoList.url,
            queryParams : Dolphin.form.getValue('queryForm'),
            title : '待办列表',
            columns : [{
                code: 'id',
                title: '订单号',
                formatter : function(val, row, index){
                    var link = $('<a>').html(val);
                    link.attr('href', 'taskInfo');
                    return link;
                }
            }, {
                code:'processInstanceId',
                title:'实例编号'
            },{
                code:'processDefinitionId',
                title:'流程名称'
            },{
                code:'description',
                title:'任务标题'
            },{
                code: 'assignee',
                title: '当前处理人'
            },{
                code:'name',
                title:'节点名称'
            }, {
                code:'owner',
                title:'下单人'
            },{
                code: 'createTime',
                title: '下单时间',
                formatter:function(val){
                    return Dolphin.longDate2string(val);
                }
            }]
        });
    };

    page.initEvent = function () {
        var _this = this;
        $("#query").click(function () {
            this.undoList.query(Dolphin.form.getValue("#queryForm"));
        });
        $("#conditionReset").click(function () {
            Dolphin.form.empty("#queryForm")
        });
    };

    window.page = page.init();
});