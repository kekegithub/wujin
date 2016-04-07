package com.hardware.api.bean.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhoupenglei on 16/4/1.
 */
public class HomeProductsResShops implements Serializable{
    private List<Shop> rows;
    private Integer total;

    public List<Shop> getRows() {
        return rows;
    }

    public void setRows(List<Shop> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
