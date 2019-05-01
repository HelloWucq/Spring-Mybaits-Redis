package com.wucq.webdemo.mappers;

import com.wucq.webdemo.Entity.Product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    Product select(@Param("id") long id);

    void update(Product product);
}