/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.api.domain;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;
import com.breezee.common.util.ContextUtil;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单信息对象
 * Created by Silence on 2016/2/11.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderInfo extends BaseInfo {

    //-1未付款, 0: 已提交, 1,已付款， 2，已确认， 3，已制作， 4，已发货， 5，已完成

    private Long userId;
    private String userMobile;
    private String userName;
    private Date issueDate;
    private List<OrderLineInfo> orderLines = new ArrayList<>();
    /**
     * 需要支付的价格
     */
    private Amount paymentAmount;
    private String paymentType = "wepay";
    private String priority;
    private Long shippingAddressId;
    private Amount shippingPrice;
    private String shippingMethod;

    private String storeName;
    /**
     * 总商品金额
     */
    private Amount subTotal;

    private String needTime;

    /**
     * 下面的流程定义的字段从界面上传递过来，而不是从数据库取出来的
     */
    private Long taskId;
    private String procDefId = "orderProcess";
    private Long procsInsId;

    private String statusName;

    private String restaurantName;

    private String restaurantImage;

    private boolean payNow;

    protected String consigneeName;

    protected String consigneeAddress;

    protected String consigneeMobile;

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public List<OrderLineInfo> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineInfo> orderLines) {
        this.orderLines = orderLines;
    }

    public void addOrderLine(OrderLineInfo orderLineInfo){
        orderLineInfo.setOrderId(this.getId());
        this.orderLines.add(orderLineInfo);
    }

    public Amount getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Amount paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Amount getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Amount shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Amount getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Amount subTotal) {
        this.subTotal = subTotal;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public Long getProcsInsId() {
        return procsInsId;
    }

    public void setProcsInsId(Long procsInsId) {
        this.procsInsId = procsInsId;
    }

    public String getNeedTime() {
        return needTime;
    }

    public void setNeedTime(String needTime) {
        this.needTime = needTime;
    }

    public String getStatusName() {
        if(statusName==null)
            return ContextUtil.getMessage("order.status."+status);
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImage() {
        return restaurantImage==null?"image_cp":restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public boolean isPayNow() {
        if(this.paymentType!=null && this.paymentType.equals("wepay") && this.status==0){
            payNow = true;
        }
        return payNow;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }
}
