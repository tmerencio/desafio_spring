package com.mercadolibre.desafio_java.model.dto.response;

import com.mercadolibre.desafio_java.model.product.Product;

import java.util.Objects;

public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Boolean freeShipping;
    private Integer prestige;

    public ProductDTO() {

    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.freeShipping = product.getFreeShipping();
        this.prestige = product.getPrestige();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public Integer getPrestige() {
        return prestige;
    }

    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(brand, that.brand) && Objects.equals(price, that.price) && Objects.equals(freeShipping, that.freeShipping) && Objects.equals(prestige, that.prestige);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, brand, price, freeShipping, prestige);
    }
}
