package org.example.backend.admin.service;

import org.example.backend.common.dto.request.DanhMucRequest;
import org.example.backend.common.dto.response.DanhMucResponse;

import java.util.List;

public interface DanhMucService {

    List<DanhMucResponse> getALL();

    DanhMucResponse getById(Long id);

    void add(DanhMucRequest request);

    void update(Long id, DanhMucRequest request);

    void delete(Long id);
}