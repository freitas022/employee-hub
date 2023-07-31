package com.freitas.employeehub.exception;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(Long id) {
        super(String.format("Employee with id %d not found.", id));
    }
}
