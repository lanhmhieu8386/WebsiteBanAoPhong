package org.example.backend.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HoaDonChiTietRequest {

    private String maHoaDonChiTiet;

    private Integer idHoaDon;

    private Long idSanPhamChiTiet;

    private Integer soLuong;

    private BigDecimal gia;
}