package org.example.backend.admin.service;

import org.example.backend.common.dto.response.HoaDonChiTietResponse;

import java.util.List;

public interface HoaDonChiTietService {

    List<HoaDonChiTietResponse> getDanhSachByHoaDonId(Integer idHoaDon);

    HoaDonChiTietResponse getDetailById(Integer id);
}