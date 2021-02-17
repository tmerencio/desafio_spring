package com.mercadolibre.desafio_java.util.comparators;

import com.mercadolibre.desafio_java.model.dto.response.ProductDTO;
import com.mercadolibre.desafio_java.model.dto.request.ProductSearchDTO;
import com.mercadolibre.desafio_java.util.comparators.impl.*;

import java.util.Comparator;

public class ComparatorFactory {
    public static final int NAME_ASC = 0;
    public static final int NAME_DESC = 1;
    public static final int PRICE_ASC = 2;
    public static final int PRICE_DESC = 3;

    public static Comparator<ProductDTO> getInstance(ProductSearchDTO productSearchDTO) {
        int orderType = NAME_ASC;

        if (productSearchDTO.getOrder() != null) {
            orderType = productSearchDTO.getOrder();
        }

        switch (orderType) {
            case NAME_ASC:
                return new ComparatorProductNameAscImpl();
            case NAME_DESC:
                return new ComparatorProductNameDescImpl();
            case PRICE_ASC:
                return new ComparatorProductPriceAscImpl();
            case PRICE_DESC:
                return new ComparatorProductPriceDescImpl();
            default:
                return new ComparatorProductNameAscImpl();
        }
    }
}
