package org.example.backend.common.repository;

import org.example.backend.common.dto.response.TaiKhoanResponse;
import org.example.backend.common.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    Optional<TaiKhoan> findByEmail(String email);


    @Query("select tk from TaiKhoan tk where tk.id = ?1")
    Optional<TaiKhoan> findByIdCustom(Integer id);

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
        from TaiKhoan tk
        left join tk.vaiTro vt
        where tk.id = :id
    """)
    Optional<TaiKhoanResponse> getDetail(@Param("id") Integer id);

    @Query("""
        select case when count(tk) > 0 then true else false end
        from TaiKhoan tk
        where tk.maTaiKhoan = :maTaiKhoan
          and (:id is null or tk.id <> :id)
    """)
    boolean existsByMaTaiKhoan(@Param("maTaiKhoan") String maTaiKhoan,
                               @Param("id") Integer id);

    @Query("""
        select case when count(tk) > 0 then true else false end
        from TaiKhoan tk
        where tk.email = :email
          and (:id is null or tk.id <> :id)
    """)
    boolean existsByEmail(@Param("email") String email,
                          @Param("id") Integer id);

    @Query("""
        select case when count(tk) > 0 then true else false end
        from TaiKhoan tk
        where tk.soDienThoai = :soDienThoai
          and (:id is null or tk.id <> :id)
    """)
    boolean existsBySoDienThoai(@Param("soDienThoai") String soDienThoai,
                                @Param("id") Integer id);

}
