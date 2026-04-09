package org.example.backend.customer.dto.request;

import lombok.Data;

@Data
public class ThemGioHangRequest {
    private Long idSanPhamChiTiet;
    private Integer soLuong;
}