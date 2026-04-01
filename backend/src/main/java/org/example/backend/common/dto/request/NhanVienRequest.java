package org.example.backend.common.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVienRequest {

    private String maNhanVien;
    private Integer idTaiKhoan;
    private String tenNhanVien;
    private String soDienThoai;
    private String diaChi;
    private LocalDateTime ngaySinh;
    private Boolean gioiTinh;
    private String email;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Boolean trangThai;
}