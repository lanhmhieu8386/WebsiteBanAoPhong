package org.example.backend.customer.repository;

import org.example.backend.customer.entity.TaiKhoanCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaiKhoanCustomerRepository extends JpaRepository<TaiKhoanCustomer, Long> {
    Optional<TaiKhoanCustomer> findByEmail(String email);

}
