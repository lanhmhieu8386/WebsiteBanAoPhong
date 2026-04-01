package org.example.backend.common.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanRequest {

    private String maTaiKhoan;
    private String tenTaiKhoan;
    private String email;
    private String matKhau;
    private String soDienThoai;
    private String diaChi;
    private Integer idVaiTro;

}