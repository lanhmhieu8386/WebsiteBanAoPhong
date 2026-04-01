package org.example.backend.customer.repository;

import org.example.backend.customer.entity.DanhMucCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucCustomerRepository extends JpaRepository<DanhMucCustomer, Long> {

    @Query("""
            select dm from DanhMucCustomer dm
            """)
    List<DanhMucCustomer> getAllDanhMuc();
}
