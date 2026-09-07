package com.eazybytes.eazystore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDto(Long orderId, String status,
                               BigDecimal totalPrice, String createdAt,
                               String trackingNumber,
                               String deliveryCarrier,
                               String estimatedDelivery,
                               List<OrderItemResponseDto> items) {
}
