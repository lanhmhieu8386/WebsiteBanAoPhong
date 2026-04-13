package org.example.backend.customer.repository;

import org.example.backend.customer.entity.KhachHangCustomer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangCustomerRepository extends CrudRepository<KhachHangCustomer, Long> {

    Optional<KhachHangCustomer> findByTaiKhoanCustomer_Id(Long id);
}
