package org.example.backend.customer.repository;

import org.example.backend.customer.entity.HoaDonChiTietCustomer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonChiTietCustomerRepository extends CrudRepository<HoaDonChiTietCustomer, Long> {
}
