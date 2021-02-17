package com.mercadolibre.desafio_java.model.dto.response;

public class ProductDetailDTO {
    private ProductDTO productDTO;
    private Integer quantity;
    private Double subTotal;

    public ProductDetailDTO() {
    }

    public ProductDetailDTO(ProductDTO productDTO, Integer quantity) {
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.subTotal = productDTO.getPrice() * quantity;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
