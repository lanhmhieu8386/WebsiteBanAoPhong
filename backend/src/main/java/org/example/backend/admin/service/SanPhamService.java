package org.example.backend.admin.service;

import org.example.backend.common.dto.request.SanPhamRequest;
import org.example.backend.common.dto.response.SanPhamDetailResponse;
import org.example.backend.common.dto.response.SanPhamResponse;

import java.util.List;

public interface SanPhamService {

    List<SanPhamResponse> getAll();

    SanPhamDetailResponse getById(Long id);

    void add(SanPhamRequest request);

    void update(Long id, SanPhamRequest request);

    void delete(Long id);

}
