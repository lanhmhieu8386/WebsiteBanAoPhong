package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.ChatLieuService;
import org.example.backend.common.entity.ChatLieu;
import org.example.backend.common.entity.ThuongHieu;
import org.example.backend.common.repository.ChatLieuRepository;
import org.example.backend.common.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Override
    public List<ChatLieu> getAll() {
        return (List<ChatLieu>) chatLieuRepository.findAll();
    }

    @Override
    public ChatLieu getById(Long id) {
        return chatLieuRepository.findById(id).orElse(null);
    }
}
