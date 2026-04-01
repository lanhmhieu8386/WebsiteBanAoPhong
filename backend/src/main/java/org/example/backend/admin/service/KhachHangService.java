package org.example.backend.admin.service;

import org.example.backend.common.dto.request.KhachHangRequest;
import org.example.backend.common.dto.response.KhachHangResponse;
import org.springframework.data.domain.Page;

public interface KhachHangService {
    Page<KhachHangResponse> getAll(String keyword, Integer trangThai, int page, int size);
    KhachHangResponse getById(Integer id);
    KhachHangResponse add(KhachHangRequest request);
    KhachHangResponse update(Integer id, KhachHangRequest request);
    void delete(Integer id);
}
