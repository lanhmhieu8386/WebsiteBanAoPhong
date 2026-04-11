package org.example.backend.common.repository;

import org.example.backend.common.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {

    @Query("""
        select case when count(ms) > 0 then true else false end
        from MauSac ms
        where ms.maMauSac = :maMauSac
          and (:id is null or ms.id <> :id)
    """)
    boolean existsByMaMauSac(@Param("maMauSac") String maMauSac,
                             @Param("id") Long id);
}