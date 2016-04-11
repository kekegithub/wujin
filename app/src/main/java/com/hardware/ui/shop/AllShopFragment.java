package com.hardware.ui.shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.MoreDiscountShopResponse;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.base.APullToRefreshListFragment;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/11.
 */
public class AllShopFragment extends APullToRefreshListFragment<AllShopFragment.ShopInfo> {

    private DisplayImageOptions options;

    public static void launch(Activity from) {
        FragmentContainerActivity.launch(from, AllShopFragment.class, null);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("全部店铺");
        options= ToolsHelper.buldDefDisplayImageOptions();
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=10;
    }

    @Override
    public int getFirstPageIndex() {
        return 1;
    }


    @Override
    protected ABaseAdapter.AbstractItemView<AllShopFragment.ShopInfo> newItemView() {
        return new ShopInfoItemView();
    }

    @Override
    protected void requestData(RefreshMode mode) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("Page", getNextPage(mode));
        requestParams.put("regionName", 1);

        startRequest(ApiConstants.MORE_POPULARITY_SHOP_LIST, requestParams, new PagingTask<MoreDiscountShopResponse>(mode) {
            @Override
            public MoreDiscountShopResponse parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, MoreDiscountShopResponse.class);
            }

            @Override
            protected List<ShopInfo> parseResult(MoreDiscountShopResponse MoreDiscountShopResponse) {
                List<ShopInfo> tempProducts = new LinkedList<>();
                if (MoreDiscountShopResponse != null && MoreDiscountShopResponse.getFlag() == 1) {
                    for (MoreDiscountShopResponse.MessageBean.RowsBean responseItem : MoreDiscountShopResponse.getMessage().getRows()) {
                        ShopInfo product = new ShopInfo();
                        product.setId(responseItem.getId());
                        product.setShopGrade(responseItem.getShopGrade());
                        product.setShopName(responseItem.getShopName());
                        product.setBusinessSphere(responseItem.getBusinessSphere());
                        product.setLogo(responseItem.getLogo());
                        product.setCompanyName(responseItem.getCompanyName());
                        product.setCompanyRegionId(responseItem.getCompanyRegionId());
                        product.setCompanyAddress(responseItem.getCompanyAddress());
                        product.setCreateDate(responseItem.getCreateDate());
                        product.setProductNumbere(responseItem.getProductNumbere());
                        product.setOrderProduct(responseItem.getOrderProduct());
                        product.setBuyerNumber(responseItem.getBuyerNumber());
                        product.setDistance(responseItem.getDistance());
                        tempProducts.add(product);
                    }
                }
                return tempProducts;
            }
        }, HttpRequestUtils.RequestType.GET);
    }

    class ShopInfoItemView extends ABaseAdapter.AbstractItemView<ShopInfo> {
        @ViewInject(id = R.id.name)
        TextView shopName;
        @ViewInject(id = R.id.businesssphere)
        TextView businesssphere ;
        @ViewInject(id = R.id.createDate)
        TextView createDate ;
        @ViewInject(id = R.id.productnumber)
        TextView productnumber;
        @ViewInject(id = R.id.orderproduct)
        TextView orderproduct ;
        @ViewInject(id = R.id.companyaddress)
        TextView companyaddress ;

        @Override
        public int inflateViewId() {
            return R.layout.more_discount_shop_list_item;
        }

        @Override
        public void bindingData(View convertView, ShopInfo data) {
            shopName.setText(data.getShopName());
            businesssphere.setText("经营模式："+data.getBusinessSphere());
            createDate.setText(data.getCreateDate()+"年");
            productnumber.setText(data.getProductNumbere()+"件产品 |");
            orderproduct.setText(data.getOrderProduct()+"位买家");
            companyaddress.setText(data.getCompanyAddress());
        }
    }

    public class ShopInfo{
        private int Id;
        private String ShopGrade;
        private String ShopName;
        private String BusinessSphere;
        private String Logo;
        private String CompanyName;
        private int CompanyRegionId;
        private String CompanyAddress;
        private int CreateDate;
        private int ProductNumbere;
        private int orderProduct;
        private int buyerNumber;
        private int distance;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getShopGrade() {
            return ShopGrade;
        }

        public void setShopGrade(String ShopGrade) {
            this.ShopGrade = ShopGrade;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public String getBusinessSphere() {
            return BusinessSphere;
        }

        public void setBusinessSphere(String BusinessSphere) {
            this.BusinessSphere = BusinessSphere;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public int getCompanyRegionId() {
            return CompanyRegionId;
        }

        public void setCompanyRegionId(int CompanyRegionId) {
            this.CompanyRegionId = CompanyRegionId;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public void setCompanyAddress(String CompanyAddress) {
            this.CompanyAddress = CompanyAddress;
        }

        public int getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(int CreateDate) {
            this.CreateDate = CreateDate;
        }

        public int getProductNumbere() {
            return ProductNumbere;
        }

        public void setProductNumbere(int ProductNumbere) {
            this.ProductNumbere = ProductNumbere;
        }

        public int getOrderProduct() {
            return orderProduct;
        }

        public void setOrderProduct(int orderProduct) {
            this.orderProduct = orderProduct;
        }

        public int getBuyerNumber() {
            return buyerNumber;
        }

        public void setBuyerNumber(int buyerNumber) {
            this.buyerNumber = buyerNumber;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
