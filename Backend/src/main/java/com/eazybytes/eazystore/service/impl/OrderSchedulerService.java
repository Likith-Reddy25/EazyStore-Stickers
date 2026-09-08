package com.eazybytes.eazystore.service.impl;

import com.eazybytes.eazystore.entity.Order;
import com.eazybytes.eazystore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
//@Slf4j
public class OrderSchedulerService {
    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void autoAdvanceOrderStatuses(){
        // fetch all the active orders that are not CANCELLED or already DELIVERED
        List<Order> activeOrders = orderRepository.findAll().stream()
                .filter(o -> !"CANCELLED".equalsIgnoreCase(o.getOrderStatus())
                        && !"DELIVERED".equalsIgnoreCase(o.getOrderStatus()))
                .toList();

        Instant now= Instant.now();

        for(Order order: activeOrders){
            if(order.getCreatedAt()==null) continue;
            // Calculate hours passed since order was placed
            long minutesPassed= Duration.between(order.getCreatedAt(),now).toMinutes();
            String currentStatus= order.getOrderStatus()!=null? order.getOrderStatus().toUpperCase():"";

            // Prototype Timeline
            // After 3 minutes -> DELIVERED
            if(minutesPassed>=15 && !"DELIVERED".equals(currentStatus)){
                order.setOrderStatus("DELIVERED");
                orderRepository.save(order);
//                log.info("Order #{} auto-updated to DELIVERED", order.getOrderId());
            }
            //After 2 minutes -> SHIPPED
            else if(minutesPassed>=7 && "PROCESSING".equals(currentStatus)){
                order.setOrderStatus("SHIPPED");
                orderRepository.save(order);
            }
            //After 1 minute -> Processing
            else if(minutesPassed>=2 && "CREATED".equalsIgnoreCase(currentStatus)){
                order.setOrderStatus("PROCESSING");
                orderRepository.save(order);
            }
        }
    }
}
