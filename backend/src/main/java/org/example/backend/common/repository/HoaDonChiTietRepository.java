package org.example.backend.common.repository;

import org.example.backend.common.dto.response.HoaDonChiTietResponse;
import org.example.backend.common.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {

    @Query("""
        SELECT new org.example.backend.common.dto.response.HoaDonChiTietResponse(
            hdct.id,
            hdct.maHoaDonChiTiet,
            hd.id,
            hd.maHoaDon,
            spct.id,
            spct.maSanPhamChiTiet,
            sp.tenSanPham,
            ms.tenMauSac,
            kc.tenKichCo,
            ha.urlAnh,
            hdct.soLuong,
            hdct.gia
        )
        FROM HoaDonChiTiet hdct
        LEFT JOIN hdct.hoaDon hd
        LEFT JOIN hdct.sanPhamChiTiet spct
        LEFT JOIN spct.sanPham sp
        LEFT JOIN spct.mauSac ms
        LEFT JOIN spct.kichCo kc
        LEFT JOIN spct.hinhAnh ha
        WHERE hd.id = :idHoaDon
        ORDER BY hdct.id ASC
    """)
    List<HoaDonChiTietResponse> getDanhSachChiTietByHoaDonId(@Param("idHoaDon") Integer idHoaDon);

    @Query("""
        SELECT new org.example.backend.common.dto.response.HoaDonChiTietResponse(
            hdct.id,
            hdct.maHoaDonChiTiet,
            hd.id,
            hd.maHoaDon,
            spct.id,
            spct.maSanPhamChiTiet,
            sp.tenSanPham,
            ms.tenMauSac,
            kc.tenKichCo,
            ha.urlAnh,
            hdct.soLuong,
            hdct.gia
        )
        FROM HoaDonChiTiet hdct
        LEFT JOIN hdct.hoaDon hd
        LEFT JOIN hdct.sanPhamChiTiet spct
        LEFT JOIN spct.sanPham sp
        LEFT JOIN spct.mauSac ms
        LEFT JOIN spct.kichCo kc
        LEFT JOIN spct.hinhAnh ha
        WHERE hdct.id = :id
    """)
    HoaDonChiTietResponse getDetailById(@Param("id") Integer id);

    @Query("""
        SELECT hdct
        FROM HoaDonChiTiet hdct
        LEFT JOIN FETCH hdct.hoaDon hd
        LEFT JOIN FETCH hdct.sanPhamChiTiet spct
        LEFT JOIN FETCH spct.sanPham sp
        LEFT JOIN FETCH spct.mauSac ms
        LEFT JOIN FETCH spct.kichCo kc
        LEFT JOIN FETCH spct.hinhAnh ha
        WHERE hdct.id = :id
    """)
    HoaDonChiTiet findEntityDetailById(@Param("id") Integer id);

    @Query("""
        SELECT hdct
        FROM HoaDonChiTiet hdct
        LEFT JOIN FETCH hdct.hoaDon hd
        LEFT JOIN FETCH hdct.sanPhamChiTiet spct
        LEFT JOIN FETCH spct.sanPham sp
        LEFT JOIN FETCH spct.mauSac ms
        LEFT JOIN FETCH spct.kichCo kc
        LEFT JOIN FETCH spct.hinhAnh ha
        WHERE hd.id = :idHoaDon
        ORDER BY hdct.id ASC
    """)
    List<HoaDonChiTiet> findAllEntityByHoaDonId(@Param("idHoaDon") Integer idHoaDon);

    List<HoaDonChiTiet> findByHoaDon_IdOrderByIdAsc(Integer idHoaDon);
}