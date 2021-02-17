package com.mercadolibre.desafio_java.model.dto.response;

import java.util.List;

public class ReceiptDTO {
    private Long receiptId;
    private Long userId;
    private List<ProductDetailDTO> products;
    private Double totalPrice;

    public ReceiptDTO () {

    }

    public ReceiptDTO (Long userId, List<ProductDetailDTO> productDetailDTOList) {
        this.userId = userId;
        this.products = productDetailDTOList;
        this.calculatePrice();
    }

    public Long getId() {
        return receiptId;
    }

    public void setId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void calculatePrice() {
        this.totalPrice = this.products.stream().mapToDouble(ProductDetailDTO::getSubTotal).sum();
    }

    public List<ProductDetailDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetailDTO> productDetailDTOList) {
        this.products = productDetailDTOList;
    }

    public void addProduct(ProductDetailDTO productDetailDTO) {
        this.products.add(productDetailDTO);
        this.calculatePrice();
    }
}
