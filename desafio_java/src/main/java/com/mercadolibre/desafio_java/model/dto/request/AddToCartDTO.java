package com.mercadolibre.desafio_java.model.dto.request;

public class AddToCartDTO {
    private Long receiptId;
    private Long productId;
    private Integer quantity;

    public AddToCartDTO() {
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
