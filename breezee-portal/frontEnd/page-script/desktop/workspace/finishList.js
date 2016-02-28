Dolphin.defaults.mockFlag = false;
$(function () {
    var page = {};

    page.connect = {
        undoList : {
            url : '/data/bpm/bpmTask/findFinishedTasks'
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
            ajaxType : 'post',
            //queryParams : Dolphin.form.getValue('queryForm'),
            title : '已办列表',
            columns : [{
                code: 'id',
                title: '任务编号',
                formatter : function(val, row, index){
                    var link = $('<a>').html(val);
                    link.attr('href', Dolphin.path.contextPath + '/v/workflow/workflowForm.htm');
                    return link;
                }
            }, {
                code:'processInstanceId',
                title:'实例编号'
            },{
                code:'processDefinitionId',
                title:'流程ID'
            },{
                code:'description',
                title:'任务标题'
            },{
                code:'startTime',
                title:'到达时间',
                formatter : function (val) {
                    return Dolphin.longDate2string(val);
                }
            },{
                code:'endTime',
                title:'完成时间',
                formatter : function (val) {
                    return Dolphin.longDate2string(val);
                }
            },{
                code: 'assignee',
                title: '最后处理人'
            },{
                code:'name',
                title:'节点名称'
            }]
        });
    };

    page.initEvent = function () {
        var _this = this;
        $("#query").click(function () {
            _this.undoList.query(Dolphin.form.getValue("#queryForm"));
        });
        $("#conditionReset").click(function () {
            Dolphin.form.empty("#queryForm")
        });
    };

    window.page = page.init();
});