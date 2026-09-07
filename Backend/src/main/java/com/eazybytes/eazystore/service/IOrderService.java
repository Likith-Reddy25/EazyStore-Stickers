package com.eazybytes.eazystore.service;

import com.eazybytes.eazystore.dto.AddressDto;
import com.eazybytes.eazystore.dto.OrderRequestDto;
import com.eazybytes.eazystore.dto.OrderResponseDto;

import java.util.List;

public interface IOrderService {
    void createOrder(OrderRequestDto requestDto);

    List<OrderResponseDto> getCustomerOrders();

    OrderResponseDto getOrderDetails(Long orderId); // <-- For looking up a single order

    void cancelOrder(Long orderId);

    void updateOrderAddress(Long orderId, AddressDto addressDto);
}
