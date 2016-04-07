package com.hardware.api.bean.home;

import java.io.Serializable;

/**
 * Created by zhoupenglei on 16/4/1.
 */
public class HomeProductsRes implements Serializable {
    private String flag;
    private HomeProductsResMsg message;
    private HomeProductsResProType ProType;
    private HomeProductsResShops shops;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public HomeProductsResMsg getMessage() {
        return message;
    }

    public void setMessage(HomeProductsResMsg message) {
        this.message = message;
    }

    public HomeProductsResProType getProType() {
        return ProType;
    }

    public void setProType(HomeProductsResProType proType) {
        ProType = proType;
    }

    public HomeProductsResShops getShops() {
        return shops;
    }

    public void setShops(HomeProductsResShops shops) {
        this.shops = shops;
    }
}
