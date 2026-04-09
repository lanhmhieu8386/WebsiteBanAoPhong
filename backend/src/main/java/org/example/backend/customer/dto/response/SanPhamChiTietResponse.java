package org.example.backend.customer.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamChiTietResponse {

    private Long id;
    private String maSanPhamChiTiet;

    private String tenMauSac;
    private String tenKichCo;

    private Integer soLuongTon;
    private BigDecimal gia;
}