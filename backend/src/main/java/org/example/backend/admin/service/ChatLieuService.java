package org.example.backend.admin.service;

import org.example.backend.common.entity.ChatLieu;
import org.example.backend.common.entity.ThuongHieu;

import java.util.List;

public interface ChatLieuService {

    List<ChatLieu> getAll();

    ChatLieu getById(Long id);
}
