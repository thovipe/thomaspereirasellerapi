package br.edu.infnet.thomaspereirasellerapi.model.exception;

public class InvalidSellerException extends RuntimeException {
    public InvalidSellerException(String message) {
        super(message);
    }
}
