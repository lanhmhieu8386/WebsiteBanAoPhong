package org.example.backend.admin.service;

import org.example.backend.common.dto.request.SanPhamChiTietRequest;
import org.example.backend.common.dto.response.SanPhamChiTietResponse;

import java.util.List;

public interface SanPhamChiTietService {

    List<SanPhamChiTietResponse> getById(Long id);

    SanPhamChiTietResponse getDetailById(Long id);

    void add(SanPhamChiTietRequest request);

    void update(Long id, SanPhamChiTietRequest request);

    void delete(Long id);
}