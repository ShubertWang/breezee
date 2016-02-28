$(function () {
    $('#submit').click(function () {
        alert('提交成功');
        location.href = '/view/order/orderDetail?id=2&bookFlag=true';
    });
});