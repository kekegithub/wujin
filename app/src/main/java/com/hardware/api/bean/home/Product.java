package com.hardware.api.bean.home;

import java.io.Serializable;

/**
 * Created by zhoupenglei on 16/4/1.
 */
public class Product implements Serializable {
    private Integer id;
    private String imgUrl;
    private String ProductName;
    private Integer MarketPrice;
    private Integer MinSalePrice;
    private String productCode;
    private String MeasureUnit;
    private Integer SaleCounts;
    private String CompanyAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Integer getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        MarketPrice = marketPrice;
    }

    public Integer getMinSalePrice() {
        return MinSalePrice;
    }

    public void setMinSalePrice(Integer minSalePrice) {
        MinSalePrice = minSalePrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getMeasureUnit() {
        return MeasureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        MeasureUnit = measureUnit;
    }

    public Integer getSaleCounts() {
        return SaleCounts;
    }

    public void setSaleCounts(Integer saleCounts) {
        SaleCounts = saleCounts;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }
}
