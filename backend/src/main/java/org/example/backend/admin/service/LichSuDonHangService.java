package org.example.backend.admin.service;

import org.example.backend.common.dto.response.LichSuDonHangResponse;

import java.util.List;

public interface LichSuDonHangService {

    List<LichSuDonHangResponse> getAll();

    LichSuDonHangResponse getDetailById(Integer id);

    List<LichSuDonHangResponse> getByHoaDonId(Integer idHoaDon);
}