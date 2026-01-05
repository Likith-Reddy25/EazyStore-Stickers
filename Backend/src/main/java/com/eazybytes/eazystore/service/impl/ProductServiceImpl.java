package com.eazybytes.eazystore.service.impl;

import com.eazybytes.eazystore.dto.ProductDto;
import com.eazybytes.eazystore.entity.Product;
import com.eazybytes.eazystore.repository.ProductRepository;
import com.eazybytes.eazystore.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getProducts()
    {
        return productRepository.findAll().stream().map(this::productDto).collect(Collectors.toList());
    }

    private ProductDto productDto(Product product){
        ProductDto productDto= new ProductDto();
        BeanUtils.copyProperties(product,productDto);
        return productDto;
//        throw new RuntimeException("Oops something has gone wrong");
    }

}
