package org.example.backend.admin.service;

import org.example.backend.common.dto.response.HoaDonResponse;

import java.util.List;

public interface HoaDonService {

    List<HoaDonResponse> getDanhSachDonHang();

    List<HoaDonResponse> getDanhSachHoaDon();

    HoaDonResponse getDetail(Integer id);

    HoaDonResponse capNhatTrangThaiDonHang(Integer id, Integer trangThaiMoi, String nguoiCapNhat);

    HoaDonResponse capNhatTrangThaiHoaDon(Integer id, Integer trangThaiMoi, String nguoiCapNhat);
}