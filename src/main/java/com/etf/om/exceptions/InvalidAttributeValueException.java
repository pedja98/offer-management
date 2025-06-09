package com.etf.om.exceptions;

public class InvalidAttributeValueException extends RuntimeException {
    public InvalidAttributeValueException(final String message) {
        super(message);
    }
}