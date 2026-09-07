package com.eazybytes.eazystore.service;

import com.eazybytes.eazystore.dto.AddressDto;
import com.eazybytes.eazystore.dto.OrderRequestDto;
import com.eazybytes.eazystore.dto.OrderResponseDto;

public interface IOrderService {
    void createOrder(OrderRequestDto requestDto);

    OrderResponseDto getOrderDetails(Long orderId);

    void cancelOrder(Long orderId);

    void updateOrderAddress(Long orderId, AddressDto addressDto);
}
