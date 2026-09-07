package com.eazybytes.eazystore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private String orderStatus;
    private BigDecimal totalPrice;
    private Instant createdAt;
    private AddressDto address;
    private List<OrderItemDto> items;
}
