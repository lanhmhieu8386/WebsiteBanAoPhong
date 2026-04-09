package org.example.backend.customer.repository;

import org.example.backend.customer.entity.GioHangChiTietCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTietCustomer, Long> {
    Optional<GioHangChiTietCustomer>
    findByGioHang_IdAndSanPhamChiTiet_Id(Long gioHangId, Long spctId);
}
