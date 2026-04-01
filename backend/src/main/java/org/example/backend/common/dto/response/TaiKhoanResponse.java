package org.example.backend.common.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanResponse {

    private Integer id;
    private String maTaiKhoan;
    private String tenTaiKhoan;
    private String email;
    private String matKhau;
    private String soDienThoai;
    private String diaChi;
    private Integer idVaiTro;
    private String maVaiTro;
    private String tenVaiTro;
    private LocalDateTime ngayTao;
}