package org.example.backend.common.repository;

import org.example.backend.common.dto.response.KhachHangResponse;
import org.example.backend.common.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    @Query("""
        select new org.example.backend.common.dto.response.KhachHangResponse(
            kh.id,
            kh.maKhachHang,
            tk.id,
            tk.tenTaiKhoan,
            kh.tenKhachHang,
            kh.ngaySinh,
            kh.soDienThoai,
            kh.email,
            kh.trangThai,
            vt.maVaiTro,
            vt.tenVaiTro
        )
        from KhachHang kh
        left join kh.taiKhoan tk
        left join tk.vaiTro vt
        where (:keyword is null
            or lower(kh.maKhachHang) like lower(concat('%', :keyword, '%'))
            or lower(kh.tenKhachHang) like lower(concat('%', :keyword, '%'))
            or lower(kh.soDienThoai) like lower(concat('%', :keyword, '%'))
            or lower(kh.email) like lower(concat('%', :keyword, '%'))
            or lower(tk.tenTaiKhoan) like lower(concat('%', :keyword, '%')))
        and (:trangThai is null or kh.trangThai = :trangThai)
    """)
    Page<KhachHangResponse> searchKhachHang(
            @Param("keyword") String keyword,
            @Param("trangThai") Integer trangThai,
            Pageable pageable
    );

    @Query("""
        select new org.example.backend.common.dto.response.KhachHangResponse(
            kh.id,
            kh.maKhachHang,
            tk.id,
            tk.tenTaiKhoan,
            kh.tenKhachHang,
            kh.ngaySinh,
            kh.soDienThoai,
            kh.email,
            kh.trangThai,
            vt.maVaiTro,
            vt.tenVaiTro
        )
        from KhachHang kh
        left join kh.taiKhoan tk
        left join tk.vaiTro vt
        where kh.id = :id
    """)
    Optional<KhachHangResponse> getDetail(@Param("id") Integer id);

    @Query("select kh from KhachHang kh where kh.maKhachHang = :maKhachHang")
    Optional<KhachHang> findByMaKhachHang(@Param("maKhachHang") String maKhachHang);

    @Query("select case when count(kh) > 0 then true else false end from KhachHang kh where kh.maKhachHang = :maKhachHang and (:id is null or kh.id <> :id)")
    boolean existsByMaKhachHang(@Param("maKhachHang") String maKhachHang, @Param("id") Integer id);

    @Query("select case when count(kh) > 0 then true else false end from KhachHang kh where kh.email = :email and (:id is null or kh.id <> :id)")
    boolean existsByEmail(@Param("email") String email, @Param("id") Integer id);

    @Query("select case when count(kh) > 0 then true else false end from KhachHang kh where kh.soDienThoai = :soDienThoai and (:id is null or kh.id <> :id)")
    boolean existsBySoDienThoai(@Param("soDienThoai") String soDienThoai, @Param("id") Integer id);

    Optional<KhachHang> findById(Integer id);

    @Query("select kh from KhachHang kh where kh.id = :id")
    Optional<KhachHang> findByIdCustom(@Param("id") Integer id);

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
        from KhachHang kh
        left join kh.taiKhoan tk
        left join tk.vaiTro vt
        where kh.id = :idKhachHang
    """)
    Optional<org.example.backend.common.dto.response.TaiKhoanResponse> getTaiKhoanByKhachHangId(
            @Param("idKhachHang") Integer idKhachHang
    );
}
