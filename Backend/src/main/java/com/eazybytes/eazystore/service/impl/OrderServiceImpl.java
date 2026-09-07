package com.eazybytes.eazystore.service.impl;

import com.eazybytes.eazystore.Constants.ApplicationConstants;
import com.eazybytes.eazystore.dto.AddressDto;
import com.eazybytes.eazystore.dto.OrderItemDto;
import com.eazybytes.eazystore.dto.OrderRequestDto;
import com.eazybytes.eazystore.dto.OrderResponseDto;
import com.eazybytes.eazystore.entity.*;
import com.eazybytes.eazystore.exception.ResourceNotFoundException;
import com.eazybytes.eazystore.repository.CustomerRepository;
import com.eazybytes.eazystore.repository.OrderRepository;
import com.eazybytes.eazystore.repository.ProductRepository;
import com.eazybytes.eazystore.service.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProfileServiceImpl profileService;
    private final CustomerRepository customerRepository;

    @Override
    public void createOrder(OrderRequestDto requestDto) {
        Customer customer=profileService.getAuthenticatedCustomer();
        // Create Order
        Order order = new Order();
        order.setCustomer(customer);
        BeanUtils.copyProperties(requestDto, order);
        order.setOrderStatus(ApplicationConstants.ORDER_STATUS_CREATED);
        // Map OrderItems
        List<OrderItem> orderItems = requestDto.items().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductID",
                            item.productId().toString()));
            orderItem.setProduct(product);
            orderItem.setQuantity(item.quantity());
            orderItem.setPrice(item.price());
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        order.setOrderStatus(ApplicationConstants.ORDER_STATUS_CREATED);
        order.setPaymentId("TEMP_ID");
        order.setPaymentStatus("PENDING");
        orderRepository.save(order);

    }
    @Override
    @Transactional
    public OrderResponseDto getOrderDetails(Long orderId){
        Order order= orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order", "orderId", orderId.toString()));

        //Map Address entity to AddressDto (using your existing AddressDto)
        AddressDto addressDto = new AddressDto();
        Address address=  (order.getCustomer() != null) ? order.getCustomer().getAddress() : null;
        if (address != null) {
            addressDto.setStreet(address.getStreet());
            addressDto.setCity(address.getCity());
            addressDto.setState(address.getState());
            addressDto.setPostalCode(address.getPostalCode());
            addressDto.setCountry(address.getCountry());
        }
        // Map OrderItems to OrderItemDto
        List<OrderItemDto> itemDtos = order.getOrderItems().stream()
                .map(item -> new OrderItemDto(
                        item.getProduct().getProductId(),
                        item.getQuantity(),
                        item.getPrice()
                )).toList();
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .address(addressDto)
                .items(itemDtos)
                .build();
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId){
        Order order= orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order", "orderId", orderId.toString()));

        String currentStatus= order.getOrderStatus()!=null? order.getOrderStatus().toUpperCase():"";

        //only Allow cancellation if order is not yet shipped/delivered
        if("SHIPPED".equals(currentStatus) || "DELIVERED".equals(currentStatus) || "CANCELLED".equals(currentStatus)){
            throw new IllegalStateException("Order cannot be cancelled as it already "+ currentStatus);
        }
        order.setOrderStatus("CANCELLED");
        orderRepository.save(order);
    }

    @Override
    public void updateOrderAddress(Long orderId, AddressDto addressDto){
        Order order= orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order", "orderId", orderId.toString()));

        String currentStatus= order.getOrderStatus()!=null? order.getOrderStatus().toUpperCase():"";
        // only allow address change if order is not yet shipped
        if("SHIPPED".equals(currentStatus) || "DELIVERED".equals(currentStatus) || "CANCELLED".equals(currentStatus)){
            throw new IllegalStateException("Shipping address cannot be modified as order is already "+ currentStatus);
        }

        Customer customer= order.getCustomer();
        if(customer!=null){
            Address address= customer.getAddress();

            if(address==null){
                address= new Address();
                address.setCustomer(customer);
            }
            address.setStreet(addressDto.getStreet());
            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setPostalCode(addressDto.getPostalCode());
            address.setCountry(addressDto.getCountry());
            customer.setAddress(address);
            customerRepository.save(customer);
        }
    }
}
