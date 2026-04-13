package org.example.backend.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonCustomerRequest {

    private List<Item> items;

    private String tenNguoiNhan;
    private String soDienThoai;
    private String diaChi;
    private Integer trangThai;


    @Data
    public static class Item {
        private Long idSanPhamChiTiet;
        private Integer soLuong;
    }
}