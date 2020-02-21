package com.example.orderservice.repository;

import com.example.orderservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByIdAndIsDeletedIsFalse(Integer id);

    Page<Customer> findAllByIsDeletedIsFalse(Pageable pageable);

    @Modifying
    @Query(nativeQuery = true,
            value = "update customer set is_deleted=TRUE, email=null, phone_number=null where id=:id")
    void deleteCustomer(@Param("id") Integer id);

    Optional<Customer> findByEmail(String email);

}
