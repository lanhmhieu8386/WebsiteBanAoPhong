package org.example.backend.common.repository;

import org.example.backend.common.dto.response.NhanVienResponse;
import org.example.backend.common.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    @Query("""
        select new org.example.backend.common.dto.response.NhanVienResponse(
            nv.id,
            nv.maNhanVien,
            tk.id,
            tk.tenTaiKhoan,
            nv.tenNhanVien,
            nv.soDienThoai,
            nv.diaChi,
            nv.ngaySinh,
            nv.gioiTinh,
            nv.email,
            nv.trangThai,
            vt.maVaiTro,
            vt.tenVaiTro
        )
        from NhanVien nv
        left join nv.taiKhoan tk
        left join tk.vaiTro vt
        where (:keyword is null
            or lower(nv.maNhanVien) like lower(concat('%', :keyword, '%'))
            or lower(nv.tenNhanVien) like lower(concat('%', :keyword, '%'))
            or lower(nv.soDienThoai) like lower(concat('%', :keyword, '%'))
            or lower(nv.email) like lower(concat('%', :keyword, '%'))
            or lower(tk.tenTaiKhoan) like lower(concat('%', :keyword, '%')))
        and (:trangThai is null or nv.trangThai = :trangThai)
    """)
    Page<NhanVienResponse> searchNhanVien(
            @Param("keyword") String keyword,
            @Param("trangThai") Boolean trangThai,
            Pageable pageable
    );

    @Query("""
        select new org.example.backend.common.dto.response.NhanVienResponse(
            nv.id,
            nv.maNhanVien,
            tk.id,
            tk.tenTaiKhoan,
            nv.tenNhanVien,
            nv.soDienThoai,
            nv.diaChi,
            nv.ngaySinh,
            nv.gioiTinh,
            nv.email,
            nv.trangThai,
            vt.maVaiTro,
            vt.tenVaiTro
        )
        from NhanVien nv
        left join nv.taiKhoan tk
        left join tk.vaiTro vt
        where nv.id = :id
    """)
    Optional<NhanVienResponse> getDetail(@Param("id") Integer id);

    @Query("select nv from NhanVien nv where nv.maNhanVien = :maNhanVien")
    Optional<NhanVien> findByMaNhanVien(@Param("maNhanVien") String maNhanVien);

    @Query("select case when count(nv) > 0 then true else false end from NhanVien nv where nv.maNhanVien = :maNhanVien and (:id is null or nv.id <> :id)")
    boolean existsByMaNhanVien(@Param("maNhanVien") String maNhanVien, @Param("id") Integer id);

    @Query("select case when count(nv) > 0 then true else false end from NhanVien nv where nv.email = :email and (:id is null or nv.id <> :id)")
    boolean existsByEmail(@Param("email") String email, @Param("id") Integer id);

    @Query("select case when count(nv) > 0 then true else false end from NhanVien nv where nv.soDienThoai = :soDienThoai and (:id is null or nv.id <> :id)")
    boolean existsBySoDienThoai(@Param("soDienThoai") String soDienThoai, @Param("id") Integer id);

    @Query("select nv from NhanVien nv where nv.id = :id")
    Optional<NhanVien> findByIdCustom(@Param("id") Integer id);

    @Query("""
        select new org.example.backend.common.dto.response.TaiKhoanResponse(
            tk.id,
            tk.maTaiKhoan,
            tk.tenTaiKhoan,
            tk.email,
            tk.matKhau,
            tk.soDienThoai,
            tk.diaChi,
            vt.id,
            vt.maVaiTro,
            vt.tenVaiTro,
            tk.ngayTao
        )
        from NhanVien nv
        left join nv.taiKhoan tk
        left join tk.vaiTro vt
        where nv.id = :idNhanVien
    """)
    Optional<org.example.backend.common.dto.response.TaiKhoanResponse> getTaiKhoanByNhanVienId(
            @Param("idNhanVien") Integer idNhanVien
    );
}
