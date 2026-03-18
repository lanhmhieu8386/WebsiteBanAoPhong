package org.example.backend.common.repository;

import org.example.backend.common.entity.DanhMuc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends CrudRepository<DanhMuc,Long> {
}
