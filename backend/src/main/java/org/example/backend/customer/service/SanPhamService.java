package org.example.backend.customer.service;

import org.example.backend.customer.dto.response.SanPhamResponse;

import java.util.List;

public interface SanPhamService {
    List<SanPhamResponse> findAll();

    List<SanPhamResponse> findByIdDanhMuc(Long idDanhMuc);

    SanPhamResponse findById(Long id);
}
