package org.example.backend.customer.repository;

import org.example.backend.customer.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {

    @Query("""
                SELECT DISTINCT s 
                FROM SanPham s
                LEFT JOIN FETCH s.sanPhamChiTietList spct
            """)
    List<SanPham> findAllWithChiTiet();

    @Query("""
                SELECT DISTINCT s 
                FROM SanPham s
                LEFT JOIN FETCH s.sanPhamChiTietList spct
                WHERE s.danhMuc.id = :idDanhMuc
            """)
    List<SanPham> findByDanhMucWithChiTiet(Long idDanhMuc);

    @Query("""
                SELECT s 
                FROM SanPham s
                LEFT JOIN FETCH s.sanPhamChiTietList spct
                WHERE s.id = :id
            """)
    Optional<SanPham> findByIdWithChiTiet(Long id);
}
