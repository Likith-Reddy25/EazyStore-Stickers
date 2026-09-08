package com.eazybytes.eazystore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderRequestDto(BigDecimal totalPrice,
                              String paymentId, String paymentStatus, List<OrderItemDto> items) {
}
