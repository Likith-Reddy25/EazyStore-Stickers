package com.eazybytes.eazystore.controller;

import com.eazybytes.eazystore.dto.AddressDto;
import com.eazybytes.eazystore.dto.ApiResponseDto;
import com.eazybytes.eazystore.dto.OrderRequestDto;
import com.eazybytes.eazystore.dto.OrderResponseDto;
import com.eazybytes.eazystore.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService iOrderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDto requestDto){
        iOrderService.createOrder(requestDto);
        return ResponseEntity.ok("Order created successfully");
    }

    // 2. Get all orders for the authenticated customer
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getCustomerOrders() {
        return ResponseEntity.ok(iOrderService.getCustomerOrders());
    }

//    Fetch Order details and Status
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderbyId(@PathVariable Long orderId){
        return ResponseEntity.ok(iOrderService.getOrderDetails(orderId));
    }

    //Cancel Order (Only if processing)
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponseDto> cancelOrder(@PathVariable Long orderId){
        iOrderService.cancelOrder(orderId);
        return ResponseEntity.ok(new ApiResponseDto("Order Cancelled Successfully"));
    }

    //Update shipping address
    @PutMapping("/{orderId}/address")
    public ResponseEntity<ApiResponseDto> updateOrderAddress(
            @PathVariable Long orderId,
            @RequestBody AddressDto addressDto
    ){
        iOrderService.updateOrderAddress(orderId, addressDto);
        return ResponseEntity.ok(new ApiResponseDto("Shipping Address updated Successfully"));
    }

}
