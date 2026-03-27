package org.example.backend.common.repository;

import org.example.backend.common.dto.response.SanPhamChiTietResponse;
import org.example.backend.common.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Long> {


    @Query("""
    SELECT new org.example.backend.common.dto.response.SanPhamChiTietResponse(
            sp.id,
            ha.urlAnh,
            sp.tenSanPham,
            dm.tenDanhMuc,
            ms.tenMauSac,
            kc.tenKichCo,
            spct.gia,
            spct.soLuongTon,
            spct.trangThai
        )
        FROM SanPhamChiTiet spct
        JOIN spct.sanPham sp
        LEFT JOIN sp.danhMuc dm
        LEFT JOIN spct.mauSac ms
        LEFT JOIN spct.kichCo kc
        LEFT JOIN spct.hinhAnh ha
        WHERE sp.id = :id
    """)
    List<SanPhamChiTietResponse> getChiTietBySanPhamId(@Param("id") Long id);
}