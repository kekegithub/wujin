package com.hardware.api.bean.home;

import java.io.Serializable;

/**
 * Created by zhoupenglei on 16/4/1.
 */
public class Shop implements Serializable {
    private Integer Id;
    private String ShopGrade;
    private String ShopName;
    private String BusinessSphere;
    private String Logo;
    private String CompanyName;
    private Integer CompanyRegionId;
    private String CompanyAddress;
    private Integer CreateDate;
    private Integer ProductNumbere;
    private Integer orderProduct;
    private Integer buyerNumber;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getShopGrade() {
        return ShopGrade;
    }

    public void setShopGrade(String shopGrade) {
        ShopGrade = shopGrade;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getBusinessSphere() {
        return BusinessSphere;
    }

    public void setBusinessSphere(String businessSphere) {
        BusinessSphere = businessSphere;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public Integer getCompanyRegionId() {
        return CompanyRegionId;
    }

    public void setCompanyRegionId(Integer companyRegionId) {
        CompanyRegionId = companyRegionId;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public Integer getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Integer createDate) {
        CreateDate = createDate;
    }

    public Integer getProductNumbere() {
        return ProductNumbere;
    }

    public void setProductNumbere(Integer productNumbere) {
        ProductNumbere = productNumbere;
    }

    public Integer getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(Integer orderProduct) {
        this.orderProduct = orderProduct;
    }

    public Integer getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(Integer buyerNumber) {
        this.buyerNumber = buyerNumber;
    }
}
