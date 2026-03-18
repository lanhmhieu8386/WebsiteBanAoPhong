package org.example.backend.common.repository;

import org.example.backend.common.entity.ChatLieu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuRepository extends CrudRepository<ChatLieu,Long> {
}
