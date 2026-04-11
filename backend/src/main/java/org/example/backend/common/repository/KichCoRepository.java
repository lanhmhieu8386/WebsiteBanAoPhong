package org.example.backend.common.repository;

import org.example.backend.common.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, Long> {

    @Query("""
        select case when count(kc) > 0 then true else false end
        from KichCo kc
        where kc.maKichCo = :maKichCo
          and (:id is null or kc.id <> :id)
    """)
    boolean existsByMaKichCo(@Param("maKichCo") String maKichCo,
                             @Param("id") Long id);
}