package com.hardware.ui.products;

import com.hardware.api.ApiConstants;
import com.hardware.bean.MoreDiscountSaleResponse;
import com.hardware.ui.base.APullToRefreshListFragment;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.support.adapter.ABaseAdapter;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MoreDiscountSaleFragment2 extends APullToRefreshListFragment{

    private class Product implements Serializable{
        private int id;
        private String imgUrl;
        private String ProductName;
        private double MarketPrice;
        private double MinSalePrice;
        private String productCode;
        private String MeasureUnit;
        private int SaleCounts;
        private String CompanyAddress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public double getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            MarketPrice = marketPrice;
        }

        public double getMinSalePrice() {
            return MinSalePrice;
        }

        public void setMinSalePrice(double minSalePrice) {
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

        public int getSaleCounts() {
            return SaleCounts;
        }

        public void setSaleCounts(int saleCounts) {
            SaleCounts = saleCounts;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            CompanyAddress = companyAddress;
        }
    }

    @Override
    protected ABaseAdapter.AbstractItemView newItemView() {
        return null;
    }

    @Override
    protected void requestData(RefreshMode mode) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("Page", getNextPage(mode));

        /*startRequest(ApiConstants.MORE_DISCOUNT_SALE_LIST, requestParams, new PagingTask<MoreDiscountSaleResponse>() {
        });*/
    }
}
