package org.example.backend.common.repository;

import org.example.backend.common.entity.ThuongHieu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends CrudRepository<ThuongHieu,Long> {
}
