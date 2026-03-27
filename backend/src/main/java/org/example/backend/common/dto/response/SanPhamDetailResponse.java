package org.example.backend.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamDetailResponse {
    private Long id;
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private Boolean trangThai;
    private Date ngayTao;

    private Long idDanhMuc;
    private String tenDanhMuc;

    private Long idThuongHieu;
    private String tenThuongHieu;

    private Long idChatLieu;
    private String tenChatLieu;
}
