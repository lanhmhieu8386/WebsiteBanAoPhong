package org.example.backend.customer.repository;

import org.example.backend.customer.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {

    @Query("""
            select dm from DanhMuc dm
            """)
    List<DanhMuc> getAllDanhMuc();
}
