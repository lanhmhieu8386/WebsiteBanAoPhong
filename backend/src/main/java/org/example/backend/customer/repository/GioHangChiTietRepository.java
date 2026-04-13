package org.example.backend.customer.repository;

import org.example.backend.customer.entity.GioHangChiTietCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTietCustomer, Long> {
    Optional<GioHangChiTietCustomer>
    findByGioHang_IdAndSanPhamChiTiet_Id(Long gioHangId, Long spctId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GioHangChiTietCustomer g WHERE g.gioHang.id = :gioHangId")
    void deleteByGioHangId(Long gioHangId);
}
