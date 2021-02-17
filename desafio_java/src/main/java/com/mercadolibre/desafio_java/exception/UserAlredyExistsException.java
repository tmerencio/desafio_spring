package com.mercadolibre.desafio_java.exception;

public class UserAlredyExistsException extends RuntimeException{
    public UserAlredyExistsException() {
        super("User alredy exists");
    }
}
