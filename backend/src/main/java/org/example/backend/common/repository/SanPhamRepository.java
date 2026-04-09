package org.example.backend.common.repository;

import org.example.backend.common.dto.response.SanPhamDetailResponse;
import org.example.backend.common.dto.response.SanPhamResponse;
import org.example.backend.common.entity.SanPham;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends CrudRepository<SanPham, Long> {

    @Query("""
                SELECT new org.example.backend.common.dto.response.SanPhamResponse(
                    sp.id,
                    sp.maSanPham,
                    sp.tenSanPham,
                    sp.moTa,
                    sp.trangThai,
                    sp.ngayTao,
                    dm.tenDanhMuc,
                    th.tenThuongHieu,
                    cl.tenChatLieu
                )
                FROM SanPham sp
                LEFT JOIN sp.danhMuc dm
                LEFT JOIN sp.thuongHieu th
                LEFT JOIN sp.chatLieu cl
            """)
    List<SanPhamResponse> getAllSanPham();

    @Query("""
            SELECT new org.example.backend.common.dto.response.SanPhamDetailResponse(
                sp.id,
                sp.maSanPham,
                sp.tenSanPham,
                sp.moTa,
                sp.trangThai,
                sp.ngayTao,
                dm.id,
                dm.tenDanhMuc,
                th.id,
                th.tenThuongHieu,
                cl.id,
                cl.tenChatLieu
            )
            FROM SanPham sp
            LEFT JOIN sp.danhMuc dm
            LEFT JOIN sp.thuongHieu th
            LEFT JOIN sp.chatLieu cl
            WHERE sp.id = :id
            """)
    SanPhamDetailResponse getById(@Param("id") Long id);

}
