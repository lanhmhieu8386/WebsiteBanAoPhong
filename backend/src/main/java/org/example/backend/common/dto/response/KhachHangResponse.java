package org.example.backend.common.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHangResponse {

    private Integer id;
    private String maKhachHang;
    private Integer idTaiKhoan;
    private String tenTaiKhoan;
    private String tenKhachHang;
    private LocalDateTime ngaySinh;
    private String soDienThoai;
    private String email;
    private Integer trangThai;
    private String maVaiTro;
    private String tenVaiTro;
}