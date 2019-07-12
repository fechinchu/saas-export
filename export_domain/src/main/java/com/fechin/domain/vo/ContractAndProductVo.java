package com.fechin.domain.vo;

import java.io.Serializable;
import java.util.Date;

public class ContractAndProductVo implements Serializable {
    private String customName;
    private String contractNo;
    private String productNo;
    private Integer cNumber;
    private String factoryName;
    private Date deliveryPeriod;
    private Date shipTime;
    private String tradeTerms;

    @Override
    public String toString() {
        return "ContractAndProductVo{" +
                "customName='" + customName + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", productNo=" + productNo +
                ", cNumber=" + cNumber +
                ", factoryName='" + factoryName + '\'' +
                ", deliveryPeriod=" + deliveryPeriod +
                ", shipTime=" + shipTime +
                ", tradeTerms='" + tradeTerms + '\'' +
                '}';
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getcNumber() {
        return cNumber;
    }

    public void setcNumber(Integer cNumber) {
        this.cNumber = cNumber;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public Date getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(Date deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }
}
