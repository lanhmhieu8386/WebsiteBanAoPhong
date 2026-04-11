package org.example.backend.admin.service;

import org.example.backend.common.dto.request.HinhAnhRequest;
import org.example.backend.common.dto.response.HinhAnhResponse;

import java.util.List;

public interface HinhAnhService {

    List<HinhAnhResponse> getAll();

    HinhAnhResponse getById(Long id);

    void add(HinhAnhRequest request);

    void update(Long id, HinhAnhRequest request);

    void delete(Long id);
}