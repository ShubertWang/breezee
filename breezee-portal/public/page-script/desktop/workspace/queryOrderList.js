Dolphin.defaults.mockFlag = false;
$(function () {
    menu.select('queryOrderList');
    var page = {};

    page.connect = {
        orderList : {
            url : '/data/oms/order/page'
        },
        seatList: {
            url : '/data/sdx/seatOrder/page'
        },
        requestList:{
            url : '/data/sdx/otherRequest/page'
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
            checkbox:false,
            columns : [{
                code: 'code',
                title: '订单信息',
                width:'200px',
                formatter : function(val, row, index){
                    return "<div><p>订单号："+val+"</p><p>订单状态："+row.statusName+"</p><p>付款方式："+row.paymentType+"</p></div>"
                }
            }, {
                code:'issueDate',
                title:'下单日期',
                width:'150px',
                formatter:function(val){
                    return Dolphin.longDate2string(val,"yyyy-MM-dd hh:mm");
                }
            },{
                code: 'subTotal.value',
                textAlign:'right',
                title: '订单金额',

                width:'85px'
            },{
                code:'orderLines',
                title:"菜品编码 - 菜品名称 - 菜品单价 - 数量",
                formatter:function(val,data){
                    var html =[];
                    html.push("<table class='table table-striped'>");
                    for(var i=0;i<val.length;i++){
                        html.push("<tr>");
                        html.push("<td>"+val[i].skuId+"</td>");
                        html.push("<td>"+val[i].note+"</td>");
                        html.push("<td>"+val[i].unitPrice.value+"</td>");
                        html.push("<td>"+val[i].quantity.value+"</td>");
                        html.push("</tr>")
                    }
                    html.push("</table>");
                    return html.join("");
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