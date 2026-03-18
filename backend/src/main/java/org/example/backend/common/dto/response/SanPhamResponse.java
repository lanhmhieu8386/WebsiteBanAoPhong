package org.example.backend.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamResponse {

    private Long id;
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private Boolean trangThai;
    private Date ngayTao;

    private String tenDanhMuc;
    private String thuongHieu;
    private String chatLieu;
}
