package org.example.backend.customer.repository;

import org.example.backend.customer.entity.SanPhamCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamCustomerRepository extends JpaRepository<SanPhamCustomer, Long> {

    @Query("""
                SELECT DISTINCT s 
                FROM SanPhamCustomer s
                LEFT JOIN FETCH s.sanPhamChiTietList spct
            """)
    List<SanPhamCustomer> findAllWithChiTiet();

    @Query("""
                SELECT DISTINCT s 
                FROM SanPhamCustomer s
                LEFT JOIN FETCH s.sanPhamChiTietList spct
                WHERE s.danhMuc.id = :idDanhMuc
            """)
    List<SanPhamCustomer> findByDanhMucWithChiTiet(Long idDanhMuc);

    @Query("""
                SELECT s 
                FROM SanPhamCustomer s
                LEFT JOIN FETCH s.sanPhamChiTietList spct
                WHERE s.id = :id
            """)
    Optional<SanPhamCustomer> findByIdWithChiTiet(Long id);
}
