package org.example.backend.common.repository;

import org.example.backend.common.entity.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Long> {

    @Query("""
        select case when count(cl) > 0 then true else false end
        from ChatLieu cl
        where cl.maChatLieu = :maChatLieu
          and (:id is null or cl.id <> :id)
    """)
    boolean existsByMaChatLieu(@Param("maChatLieu") String maChatLieu,
                               @Param("id") Long id);
}