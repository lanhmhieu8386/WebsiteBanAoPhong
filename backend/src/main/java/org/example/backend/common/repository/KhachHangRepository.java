package org.example.backend.common.repository;

import org.example.backend.common.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    // Lãnh Minh Hiếu
    Optional<KhachHang> findByTaiKhoan_Email(String email);

    Optional<KhachHang> findByEmail(String email);
}
