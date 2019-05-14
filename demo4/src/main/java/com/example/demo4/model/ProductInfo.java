package com.example.demo4.model;

import lombok.Data;

/**
 * @author houxuebo on 2019-05-13 12:21
 */
@Data
public class ProductInfo {

    private int productId;
    private String productName;

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                '}';
    }
}
