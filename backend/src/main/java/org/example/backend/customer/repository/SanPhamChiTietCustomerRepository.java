package org.example.backend.customer.repository;

import org.example.backend.customer.entity.SanPhamChiTietCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamChiTietCustomerRepository extends JpaRepository<SanPhamChiTietCustomer, Long> {
}
