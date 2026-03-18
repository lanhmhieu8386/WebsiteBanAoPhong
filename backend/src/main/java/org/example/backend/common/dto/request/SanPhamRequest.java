package org.example.backend.common.dto.request;

import lombok.Data;

@Data
public class SanPhamRequest {

    private String maSanPham;

    private String tenSanPham;

    private String moTa;

    private Boolean trangThai;

    private Long idDanhMuc;

    private Long idThuongHieu;

    private Long idChatLieu;

}
