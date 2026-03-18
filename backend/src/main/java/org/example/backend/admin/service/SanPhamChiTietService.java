package org.example.backend.admin.service;

import org.example.backend.common.dto.response.SanPhamChiTietResponse;

import java.util.List;

public interface SanPhamChiTietService {

    List<SanPhamChiTietResponse> getById(Long id);
}
