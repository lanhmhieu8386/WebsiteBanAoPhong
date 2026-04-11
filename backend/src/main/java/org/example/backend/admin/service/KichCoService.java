package org.example.backend.admin.service;

import org.example.backend.common.dto.request.KichCoRequest;
import org.example.backend.common.dto.response.KichCoResponse;

import java.util.List;

public interface KichCoService {

    List<KichCoResponse> getAll();

    KichCoResponse getById(Long id);

    void add(KichCoRequest request);

    void update(Long id, KichCoRequest request);

    void delete(Long id);
}