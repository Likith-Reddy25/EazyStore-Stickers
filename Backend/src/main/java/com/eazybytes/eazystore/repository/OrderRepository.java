package com.eazybytes.eazystore.repository;

import com.eazybytes.eazystore.entity.Contact;
import com.eazybytes.eazystore.entity.Customer;
import com.eazybytes.eazystore.entity.Order;
import com.eazybytes.eazystore.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders o WHERE o.customer_id = :customerId ORDER BY o.created_at DESC", nativeQuery = true)
    List<Order> findOrdersByCustomerWithNativeQuery(@Param("customerId") Long customerId);

    List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);

    /**
     * Fetch orders by status (e.g., 'PROCESSING', 'SHIPPED')
     */
    List<Order> findByOrderStatus(String orderStatus);

    /**
     * Update order status directly via query
     */
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = :orderStatus, o.updatedAt = CURRENT_TIMESTAMP, o.updatedBy = :updatedBy WHERE o.orderId = :orderId")
    int updateOrderStatus(@Param("orderId") Long orderId,
                          @Param("orderStatus") String orderStatus,
                          @Param("updatedBy") String updatedBy);
}