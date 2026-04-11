package org.example.backend.admin.service;

import org.example.backend.common.dto.request.MauSacRequest;
import org.example.backend.common.dto.response.MauSacResponse;

import java.util.List;

public interface MauSacService {

    List<MauSacResponse> getAll();

    MauSacResponse getById(Long id);

    void add(MauSacRequest request);

    void update(Long id, MauSacRequest request);

    void delete(Long id);
}