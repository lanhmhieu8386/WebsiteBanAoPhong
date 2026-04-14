package org.example.backend.common.repository;

import org.example.backend.common.dto.response.LichSuDonHangResponse;
import org.example.backend.common.entity.LichSuDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LichSuDonHangRepository extends JpaRepository<LichSuDonHang, Integer> {

    @Query("""
        SELECT new org.example.backend.common.dto.response.LichSuDonHangResponse(
            ls.id,
            ls.maLichSu,
            ls.idTrangThai,
            ls.thoiGian,
            ls.ghiChu,
            ls.idHoaDon
        )
        FROM LichSuDonHang ls
        ORDER BY ls.thoiGian DESC, ls.id DESC
    """)
    List<LichSuDonHangResponse> getAllResponse();

    @Query("""
        SELECT new org.example.backend.common.dto.response.LichSuDonHangResponse(
            ls.id,
            ls.maLichSu,
            ls.idTrangThai,
            ls.thoiGian,
            ls.ghiChu,
            ls.idHoaDon
        )
        FROM LichSuDonHang ls
        WHERE ls.id = :id
    """)
    LichSuDonHangResponse getDetailById(@Param("id") Integer id);

    @Query("""
        SELECT new org.example.backend.common.dto.response.LichSuDonHangResponse(
            ls.id,
            ls.maLichSu,
            ls.idTrangThai,
            ls.thoiGian,
            ls.ghiChu,
            ls.idHoaDon
        )
        FROM LichSuDonHang ls
        WHERE ls.idHoaDon = :idHoaDon
        ORDER BY ls.thoiGian DESC, ls.id DESC
    """)
    List<LichSuDonHangResponse> getByHoaDonId(@Param("idHoaDon") Integer idHoaDon);
}