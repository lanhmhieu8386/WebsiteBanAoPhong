package org.example.backend.customer.repository;

import org.example.backend.customer.entity.GioHangCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHangCustomer, Long> {
    Optional<GioHangCustomer> findByTaiKhoanCustomer_Id(Long id);

}
