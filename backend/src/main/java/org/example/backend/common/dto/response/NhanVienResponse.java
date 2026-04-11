package org.example.backend.common.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVienResponse {

    private Integer id;
    private String maNhanVien;
    private Integer idTaiKhoan;
    private String tenTaiKhoan;
    private String tenNhanVien;
    private String soDienThoai;
    private String diaChi;
    private LocalDate ngaySinh;
    private Boolean gioiTinh;
    private String email;
    private Boolean trangThai;
    private String maVaiTro;
    private String tenVaiTro;
}