$(function () {
});

function confirmOrder(type){
    var orderId = REQUEST_MAP.body.id;
    var data = Dolphin.form.getValue('#approveForm');
    console.log("orderId："+orderId+",type："+type);
    console.log(data);

    /*ajax*/
    alert('处理成功');
    Dolphin.goUrl('/order/employeeOrder');
}