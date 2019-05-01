package com.wucq.webdemo.Entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Product implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;
    @Getter
    @Setter
    long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private long price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}