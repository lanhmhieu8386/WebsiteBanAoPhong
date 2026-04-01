package org.example.backend.admin.service;

import org.example.backend.common.dto.request.NhanVienRequest;
import org.example.backend.common.dto.response.NhanVienResponse;
import org.springframework.data.domain.Page;

public interface NhanVienService {
    Page<NhanVienResponse> getAll(String keyword, Boolean trangThai, int page, int size);
    NhanVienResponse getById(Integer id);
    NhanVienResponse add(NhanVienRequest request);
    NhanVienResponse update(Integer id, NhanVienRequest request);
    void delete(Integer id);
}
