package com.mercadolibre.desafio_java.model.dto.request;

public class ProductSearchDTO {
    private String category;
    private String brand;
    private Boolean freeShipping;
    private Double priceTopLimit;
    private Double priceBelowLimit;
    private Integer prestige;
    private Integer order;

    public ProductSearchDTO() {
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public void setPriceTopLimit(Double priceTopLimit) {
        this.priceTopLimit = priceTopLimit;
    }

    public void setPriceBelowLimit(Double priceBelowLimit) {
        this.priceBelowLimit = priceBelowLimit;
    }

    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public Double getPriceTopLimit() {
        return priceTopLimit;
    }

    public Double getPriceBelowLimit() {
        return priceBelowLimit;
    }

    public Integer getPrestige() {
        return prestige;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
