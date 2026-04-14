package org.example.backend.common.repository;

import org.example.backend.common.dto.response.HoaDonResponse;
import org.example.backend.common.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    @Query("""
        SELECT new org.example.backend.common.dto.response.HoaDonResponse(
            hd.id,
            hd.maHoaDon,
            kh.id,
            kh.tenKhachHang,
            nv.id,
            nv.tenNhanVien,
            hd.diaChiGiaoHang,
            hd.soDienThoai,
            hd.tongTien,
            hd.ngayTao,
            hd.ngayCapNhat,
            hd.nguoiTao,
            hd.nguoiCapNhat,
            hd.trangThai
        )
        FROM HoaDon hd
        LEFT JOIN hd.khachHang kh
        LEFT JOIN hd.nhanVien nv
        WHERE hd.trangThai IN (1, 2, 3)
        ORDER BY hd.ngayTao DESC
    """)
    List<HoaDonResponse> getDanhSachDonHang();

    @Query("""
        SELECT new org.example.backend.common.dto.response.HoaDonResponse(
            hd.id,
            hd.maHoaDon,
            kh.id,
            kh.tenKhachHang,
            nv.id,
            nv.tenNhanVien,
            hd.diaChiGiaoHang,
            hd.soDienThoai,
            hd.tongTien,
            hd.ngayTao,
            hd.ngayCapNhat,
            hd.nguoiTao,
            hd.nguoiCapNhat,
            hd.trangThai
        )
        FROM HoaDon hd
        LEFT JOIN hd.khachHang kh
        LEFT JOIN hd.nhanVien nv
        WHERE hd.trangThai IN (4, 5)
        ORDER BY hd.ngayTao DESC
    """)
    List<HoaDonResponse> getDanhSachHoaDon();

    @Query("""
        SELECT new org.example.backend.common.dto.response.HoaDonResponse(
            hd.id,
            hd.maHoaDon,
            kh.id,
            kh.tenKhachHang,
            nv.id,
            nv.tenNhanVien,
            hd.diaChiGiaoHang,
            hd.soDienThoai,
            hd.tongTien,
            hd.ngayTao,
            hd.ngayCapNhat,
            hd.nguoiTao,
            hd.nguoiCapNhat,
            hd.trangThai
        )
        FROM HoaDon hd
        LEFT JOIN hd.khachHang kh
        LEFT JOIN hd.nhanVien nv
        WHERE hd.id = :id
    """)
    HoaDonResponse getDetailResponseById(@Param("id") Integer id);
}