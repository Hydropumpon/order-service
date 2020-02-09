package com.example.orderservice.repository;

import com.example.orderservice.entity.Customer;
import com.example.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomer(Customer customer);

    @Modifying
    @Query(nativeQuery = true,
            value = "update orders set amount=:amount, check_date=:checkDate, state=:state where id=:id")
    void updateOrder(@Param("id") Integer id, @Param("amount") BigDecimal amount,
                     @Param("checkDate") LocalDate approveDate, @Param("state") String state);
}
