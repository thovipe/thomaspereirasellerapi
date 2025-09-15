package br.edu.infnet.thomaspereirasellerapi.model.exception;

public class InvalidMonthReferenceException extends RuntimeException {
    public InvalidMonthReferenceException(String message) {
        super(message);
    }
}
