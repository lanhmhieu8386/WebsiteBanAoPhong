package org.example.backend.customer.repository;

import org.example.backend.customer.entity.HoaDonCustomer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonCustomerRepository extends CrudRepository<HoaDonCustomer, Long> {
    List<HoaDonCustomer> findByKhachHang_Id(Long id);

    Optional<HoaDonCustomer> findByIdAndKhachHang_Id(Long id, Long khachHangId);
}
