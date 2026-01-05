package com.eazybytes.eazystore.service;

import com.eazybytes.eazystore.dto.ProductDto;
import com.eazybytes.eazystore.entity.Product;

import java.util.List;

public interface IProductService {
    List<ProductDto> getProducts();
}
