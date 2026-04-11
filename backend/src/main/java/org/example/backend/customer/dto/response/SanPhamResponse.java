package org.example.backend.customer.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamResponse {

    private Integer id;
    private String maSanPham;
    private String tenSanPham;
    private String moTa;

    // Thông tin liên quan
    private String tenDanhMuc;
    private String tenThuongHieu;
    private String tenChatLieu;

    // Giá hiển thị (min)
    private BigDecimal giaMin;

    // Danh sách biến thể
    private List<SanPhamChiTietResponse> chiTietList;
}