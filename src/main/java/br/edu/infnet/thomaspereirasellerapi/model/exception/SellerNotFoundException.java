package br.edu.infnet.thomaspereirasellerapi.model.exception;

public class SellerNotFoundException extends RuntimeException {
    public SellerNotFoundException(String message) {
        super(message);
    }
}
