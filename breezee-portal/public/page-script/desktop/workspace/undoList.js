Dolphin.defaults.mockFlag = false;
$(function () {
    var page = {};

    page.connect = {
        undoList : {
            url : '/data/bpm/bpmTask/findUndoTasks'
        }
    };

    page.init = function () {
        this.initPage();
        this.initEvent();

        return this;
    };

    page.initPage = function () {
        var _this = this;

        menu.select('workspace-undoList');
        this.undoList = new Dolphin.LIST({
            panel : '#list',
            url : _this.connect.undoList.url,
            ajaxType:'post',
            title : '待办列表',
            columns : [{
                code: 'businessKey',
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
                code: 'assignee',
                title: '当前处理人'
            },{
                code:'name',
                title:'节点名称'
            }, {
                code:'userId',
                title:'下单人'
            },{
                code: 'createTime',
                title: '下单时间',
                formatter:function(val){
                    return Dolphin.longDate2string(val);
                }
            },{
                code: 'paymentAmount',
                title: '支付金额'
            },{
                code: 'shippingMethod',
                title: '支付方式'
            },{
                code: 'subTotal',
                title: '商品总额'
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