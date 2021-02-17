package com.mercadolibre.desafio_java.service.impl;

import com.mercadolibre.desafio_java.exception.NoDataFoundException;
import com.mercadolibre.desafio_java.model.dto.request.AddToCartDTO;
import com.mercadolibre.desafio_java.model.dto.request.PurchaseRequestDTO;
import com.mercadolibre.desafio_java.model.dto.response.ProductDTO;
import com.mercadolibre.desafio_java.model.dto.response.ProductDetailDTO;
import com.mercadolibre.desafio_java.model.product.Product;
import com.mercadolibre.desafio_java.repository.IProductRepository;
import com.mercadolibre.desafio_java.model.dto.response.ReceiptDTO;
import com.mercadolibre.desafio_java.repository.IPurchaseRepository;
import com.mercadolibre.desafio_java.service.IPurchaseService;
import com.mercadolibre.desafio_java.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseServiceImpl implements IPurchaseService {
    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private IPurchaseRepository iPurchaseRepository;

    @Autowired
    private IStockService iStockService;

    private final String STATUS_HEADER = "status";
    private final String ORDER_HEADER = "order";
    private final String STATUS_COMPLETED = "COMPLETED";
    private final String STATUS_PENDING = "PENDING";

    private List<ReceiptDTO> receiptDTOList;

    public PurchaseServiceImpl() {
        this.receiptDTOList = new ArrayList<>();
    }

    @Override
    public Map<String, Object> generatePurchase(PurchaseRequestDTO purchaseRequestDTO) {
        List<ProductDetailDTO> productDetailDTOList = new ArrayList<>();

        purchaseRequestDTO.getPurchaseDTOList().forEach(purchaseDTO -> {
                    Product product = iProductRepository.findById(purchaseDTO.getProductId());
                    iStockService.verifyStock(product, purchaseDTO.getQuantity());
                    ProductDTO productDTO = new ProductDTO(product);
                    productDetailDTOList.add(new ProductDetailDTO(productDTO, purchaseDTO.getQuantity()));
                }
        );

        ReceiptDTO receiptDTO = this.generateReceipt(purchaseRequestDTO.getUserId(), productDetailDTOList);

        Map<String, Object> map = new HashMap<>();

        map.put(ORDER_HEADER, receiptDTO);
        map.put(STATUS_HEADER, STATUS_PENDING);

        return map;
    }

    private ReceiptDTO generateReceipt(Long userId, List<ProductDetailDTO> productDetailDTOList) {
        ReceiptDTO receiptDTO = new ReceiptDTO(userId, productDetailDTOList);

        receiptDTO.setId(iPurchaseRepository.nextId());

        this.receiptDTOList.add(receiptDTO);

        return receiptDTO;
    }

    @Override
    public Map<String, Object> confirmPurchase(Long id) {
        ReceiptDTO receipt = this.receiptDTOList.stream()
                .filter(receiptDTO -> receiptDTO.getId().equals(id))
                .findFirst()
                .orElseThrow(NoDataFoundException::new);

        receipt.getProducts().forEach(productDetailDTO -> {
            Product product = iProductRepository.findById(productDetailDTO.getProductDTO().getId());
            iStockService.updateStock(product, productDetailDTO.getQuantity());
        });

        iPurchaseRepository.save(receipt);

        Map<String, Object> map = new HashMap<>();

        map.put(ORDER_HEADER, receipt);
        map.put(STATUS_HEADER, STATUS_COMPLETED);

        return map;
    }

    @Override
    public Map<String, Object> addProduct(AddToCartDTO addToCartDTO) {
        ReceiptDTO receipt = this.receiptDTOList.stream()
                .filter(receiptDTO -> receiptDTO.getId().equals(addToCartDTO.getReceiptId()))
                .findFirst()
                .orElseThrow(NoDataFoundException::new);

        ProductDTO productDTO = new ProductDTO(iProductRepository.findById(addToCartDTO.getProductId()));

        receipt.addProduct(new ProductDetailDTO(productDTO, addToCartDTO.getQuantity()));

        Map<String, Object> map = new HashMap<>();

        map.put(ORDER_HEADER, receipt);
        map.put(STATUS_HEADER, STATUS_PENDING);

        return map;
    }
}
