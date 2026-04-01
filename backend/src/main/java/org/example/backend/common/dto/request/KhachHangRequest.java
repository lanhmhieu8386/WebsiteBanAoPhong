package org.example.backend.common.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHangRequest {

    private String maKhachHang;
    private Integer idTaiKhoan;
    private String tenKhachHang;
    private LocalDateTime ngaySinh;
    private String soDienThoai;
    private String email;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
}