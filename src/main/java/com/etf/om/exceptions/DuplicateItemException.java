package com.etf.om.exceptions;

public class DuplicateItemException extends RuntimeException {
    public DuplicateItemException(final String message) {
        super(message);
    }
}
