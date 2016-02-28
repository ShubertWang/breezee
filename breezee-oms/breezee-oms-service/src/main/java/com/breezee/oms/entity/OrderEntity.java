/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.entity;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;
import com.breezee.oms.api.domain.OrderInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Silence on 2016/2/11.
 */
@Entity
@Table(name = "OMS_TF_ORDER")
public class OrderEntity extends BaseInfo {

    private Long userId;
    private Date issueDate;
    private Set<OrderLineEntity> orderLines;
    /**
     * 需要支付的价格
     */
    private Double paymentAmount;
    private String currencyCode;
    private String paymentType;
    private String priority;
    private Long shippingAddressId;
    private Double shippingPrice;
    private String shippingMethod;
    private String storeName;
    private String needTime;
    /**
     * 总商品金额
     */
    private Double subTotal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "ORDER_CODE", unique = true, updatable = false, nullable = false, length = 64)
    public String getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getCreator() {
        return creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getUpdator() {
        return updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public int getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy(value = "skuId DESC")
    public Set<OrderLineEntity> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLineEntity> orderLines) {
        this.orderLines = orderLines;
    }

    public void addOrderLine(OrderLineEntity orderLineEntity){
        if(this.orderLines==null)
            this.orderLines = new HashSet<>();
        orderLineEntity.setOrder(this);
        this.orderLines.add(orderLineEntity);
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
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

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getNeedTime() {
        return needTime;
    }

    public void setNeedTime(String needTime) {
        this.needTime = needTime;
    }

    public OrderInfo toInfo(){
        OrderInfo info = new OrderInfo();
        cloneAttribute(info);
        info.setUserId(this.getUserId());
        info.setIssueDate(this.getIssueDate());
        info.setPaymentAmount(new Amount(this.getCurrencyCode(),this.getPaymentAmount()));
        info.setPaymentType(this.getPaymentType());
        info.setPriority(this.getPriority());
        info.setShippingAddressId(this.getShippingAddressId());
        info.setShippingMethod(this.getShippingMethod());
        info.setShippingPrice(new Amount(this.getCurrencyCode(),this.getShippingPrice()));
        info.setStoreName(this.getStoreName());
        info.setSubTotal(new Amount(this.currencyCode,this.getSubTotal()));
        if(this.getOrderLines()!=null && this.getOrderLines().size()>0){
            this.getOrderLines().forEach(a->{
                info.addOrderLine(a.toInfo());
            });
        }
        info.setNeedTime(this.getNeedTime());
        return info;
    }

    public OrderEntity parse(OrderInfo info){
        info.cloneAttribute(this);
        this.setStoreName(info.getStoreName());
        this.setShippingMethod(info.getShippingMethod());
        this.setShippingAddressId(info.getShippingAddressId());
        this.setCurrencyCode(info.getSubTotal().getCurrencyCode());
        this.setIssueDate(info.getIssueDate());
        if(info.getPaymentAmount()!=null)
            this.setPaymentAmount(info.getPaymentAmount().getValue());
        this.setPaymentType(info.getPaymentType());
        this.setPriority(info.getPriority());
        this.setShippingPrice(info.getShippingPrice() == null ? new Amount("CNY",new Double(0)).getValue() : info.getShippingPrice().getValue());
        this.setSubTotal(info.getSubTotal().getValue());
        this.setUserId(info.getUserId());
        this.setNeedTime(info.getNeedTime());
        if(info.getOrderLines().size()>0){
            info.getOrderLines().forEach(a->{
                this.addOrderLine(new OrderLineEntity().parse(a));
            });
        }
        return this;
    }


}
