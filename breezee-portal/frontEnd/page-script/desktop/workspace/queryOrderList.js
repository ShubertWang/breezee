Dolphin.defaults.mockFlag = false;
$(function () {
    var page = {};

    page.connect = {
        orderList : {
            url : '/data/oms/order/page'
        }
    };

    page.init = function () {
        this.initPage();
        this.initEvent();

        return this;
    };

    page.initPage = function () {
        var _this = this;
        this.orderList = new Dolphin.LIST({
            panel : '#list',
            url : _this.connect.orderList.url,
            ajaxType:'post',
            queryParams : Dolphin.form.getValue('queryForm'),
            title : '订单列表',
            columns : [{
                code: 'code',
                title: '订单号',
                formatter : function(val, row, index){
                    var link = $('<a>').html(val);
                    link.attr('href', 'taskInfo');
                    return link;
                }
            }, {
                code:'issueDate',
                title:'下单日期'
            },{
                code:'userId',
                title:'下单人'
            },{
                code: 'paymentAmount',
                title: '支付金额',
                formatter : function(val, row, index){
                    return val.value;
                }
            },{
                code:'shippingMethod',
                title:'支付方式'
            }, {
                code:'subTotal',
                title:'商品金额',
                formatter : function(val, row, index){
                    return val.value;
                }
            }]
        });
    };

    page.initEvent = function () {
        var _this = this;
        $("#query").click(function () {
            _this.orderList.query(Dolphin.form.getValue("#queryForm"));
        });
        $("#conditionReset").click(function () {
            Dolphin.form.empty("#queryForm")
        });
    };

    window.page = page.init();
});