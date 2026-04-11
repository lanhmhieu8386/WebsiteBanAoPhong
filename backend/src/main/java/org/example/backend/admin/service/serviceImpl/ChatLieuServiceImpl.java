package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.ChatLieuService;
import org.example.backend.common.dto.request.ChatLieuRequest;
import org.example.backend.common.dto.response.ChatLieuResponse;
import org.example.backend.common.entity.ChatLieu;
import org.example.backend.common.repository.ChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Override
    public List<ChatLieuResponse> getAll() {
        List<ChatLieu> list = chatLieuRepository.findAll();

        return list.stream().map(item -> ChatLieuResponse.builder()
                .id(item.getId())
                .maChatLieu(item.getMaChatLieu())
                .tenChatLieu(item.getTenChatLieu())
                .moTa(item.getMoTa())
                .trangThai(item.getTrangThai())
                .build()
        ).toList();
    }

    @Override
    public ChatLieuResponse getById(Long id) {
        ChatLieu chatLieu = chatLieuRepository.findById(id).orElse(null);

        if (chatLieu == null) {
            return null;
        }

        return ChatLieuResponse.builder()
                .id(chatLieu.getId())
                .maChatLieu(chatLieu.getMaChatLieu())
                .tenChatLieu(chatLieu.getTenChatLieu())
                .moTa(chatLieu.getMoTa())
                .trangThai(chatLieu.getTrangThai())
                .build();
    }

    @Override
    public void add(ChatLieuRequest request) {
        validateTrung(request, null);

        ChatLieu chatLieu = ChatLieu.builder()
                .maChatLieu(request.getMaChatLieu())
                .tenChatLieu(request.getTenChatLieu())
                .moTa(request.getMoTa())
                .trangThai(request.getTrangThai() != null ? request.getTrangThai() : true)
                .build();

        chatLieuRepository.save(chatLieu);
    }

    @Override
    public void update(Long id, ChatLieuRequest request) {
        ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chất liệu"));

        validateTrung(request, id);

        chatLieu.setMaChatLieu(request.getMaChatLieu());
        chatLieu.setTenChatLieu(request.getTenChatLieu());
        chatLieu.setMoTa(request.getMoTa());
        chatLieu.setTrangThai(request.getTrangThai());

        chatLieuRepository.save(chatLieu);
    }

    @Override
    public void delete(Long id) {
        ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chất liệu"));

        chatLieu.setTrangThai(false);
        chatLieuRepository.save(chatLieu);
    }

    private void validateTrung(ChatLieuRequest request, Long id) {
        if (chatLieuRepository.existsByMaChatLieu(request.getMaChatLieu(), id)) {
            throw new RuntimeException("Mã chất liệu đã tồn tại");
        }
    }
}