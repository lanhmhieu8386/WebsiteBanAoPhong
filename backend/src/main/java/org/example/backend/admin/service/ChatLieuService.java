package org.example.backend.admin.service;

import org.example.backend.common.dto.request.ChatLieuRequest;
import org.example.backend.common.dto.response.ChatLieuResponse;

import java.util.List;

public interface ChatLieuService {

    List<ChatLieuResponse> getAll();

    ChatLieuResponse getById(Long id);

    void add(ChatLieuRequest request);

    void update(Long id, ChatLieuRequest request);

    void delete(Long id);
}