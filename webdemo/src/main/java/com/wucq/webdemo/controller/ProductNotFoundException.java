package com.wucq.webdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1567221797190159060L;

    public ProductNotFoundException(long productId) {
        super("Could not find product " + productId + ".");
    }
}