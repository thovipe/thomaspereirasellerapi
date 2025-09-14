package br.edu.infnet.thomaspereirasellerapi.model.exception;

public class InvalidItemValueException extends RuntimeException{
    public InvalidItemValueException(String message) {
        super(message);
    }
}
