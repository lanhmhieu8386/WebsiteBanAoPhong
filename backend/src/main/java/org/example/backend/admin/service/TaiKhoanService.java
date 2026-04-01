package org.example.backend.admin.service;

import org.example.backend.common.dto.request.TaiKhoanRequest;
import org.example.backend.common.dto.response.TaiKhoanResponse;

public interface TaiKhoanService {
    TaiKhoanResponse getTaiKhoanNhanVien(Integer idNhanVien);

    TaiKhoanResponse themTaiKhoanChoNhanVien(Integer idNhanVien, TaiKhoanRequest request);

    TaiKhoanResponse capNhatTaiKhoanNhanVien(Integer idNhanVien, TaiKhoanRequest request);

    void xoaTaiKhoanNhanVien(Integer idNhanVien);

    TaiKhoanResponse getTaiKhoanKhachHang(Integer idKhachHang);

    TaiKhoanResponse themTaiKhoanChoKhachHang(Integer idKhachHang, TaiKhoanRequest request);

    TaiKhoanResponse capNhatTaiKhoanKhachHang(Integer idKhachHang, TaiKhoanRequest request);

    void xoaTaiKhoanKhachHang(Integer idKhachHang);
}
