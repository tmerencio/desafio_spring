package com.mercadolibre.desafio_java.model.dto.request;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRequestDTO {
    private Long userId;
    private List<PurchaseDTO> purchaseDTOList;

    public PurchaseRequestDTO() {
        this.purchaseDTOList = new ArrayList<>();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<PurchaseDTO> getPurchaseDTOList() {
        return purchaseDTOList;
    }

    public void setPurchaseDTOList(List<PurchaseDTO> purchaseDTOList) {
        this.purchaseDTOList = purchaseDTOList;
    }

    public void addPurchaseDTO(PurchaseDTO purchaseDTO) {
        this.purchaseDTOList.add(purchaseDTO);
    }
}
