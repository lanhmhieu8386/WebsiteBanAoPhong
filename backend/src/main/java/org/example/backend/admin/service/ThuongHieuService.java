package org.example.backend.admin.service;

import org.example.backend.common.dto.request.ThuongHieuRequest;
import org.example.backend.common.dto.response.ThuongHieuResponse;

import java.util.List;

public interface ThuongHieuService {

    List<ThuongHieuResponse> getAll();

    ThuongHieuResponse getById(Long id);

    void add(ThuongHieuRequest request);

    void update(Long id, ThuongHieuRequest request);

    void delete(Long id);
}