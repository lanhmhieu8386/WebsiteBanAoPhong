package org.example.backend.common.repository;

import org.example.backend.common.dto.response.SanPhamChiTietResponse;
import org.example.backend.common.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Long> {

    @Query("""
        SELECT new org.example.backend.common.dto.response.SanPhamChiTietResponse(
            spct.id,
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

    @Query("""
        SELECT new org.example.backend.common.dto.response.SanPhamChiTietResponse(
            spct.id,
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
        WHERE spct.id = :id
    """)
    Optional<SanPhamChiTietResponse> getDetailById(@Param("id") Long id);

    @Query("select spct from SanPhamChiTiet spct where spct.id = :id")
    Optional<SanPhamChiTiet> findByIdCustom(@Param("id") Long id);

    @Query("""
        select case when count(spct) > 0 then true else false end
        from SanPhamChiTiet spct
        where spct.maSanPhamChiTiet = :maSanPhamChiTiet
          and (:id is null or spct.id <> :id)
    """)
    boolean existsByMaSanPhamChiTiet(@Param("maSanPhamChiTiet") String maSanPhamChiTiet,
                                     @Param("id") Long id);
}