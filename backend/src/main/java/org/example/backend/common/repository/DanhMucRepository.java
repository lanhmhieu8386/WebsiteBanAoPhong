package org.example.backend.common.repository;

import org.example.backend.common.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {

    @Query("""
        select case when count(dm) > 0 then true else false end
        from DanhMuc dm
        where dm.maDanhMuc = :maDanhMuc
          and (:id is null or dm.id <> :id)
    """)
    boolean existsByMaDanhMuc(@Param("maDanhMuc") String maDanhMuc,
                              @Param("id") Long id);

    @Query("select dm from DanhMuc dm where dm.trangThai = true")
    List<DanhMuc> findAllActive();
}