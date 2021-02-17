package com.mercadolibre.desafio_java.exception;

public class NoStockException extends RuntimeException {
    public NoStockException(Integer unitsLeft, String productName) {
        super(
                String.format("There's only %d units of the product: %s", unitsLeft, productName)
        );
    }
}
