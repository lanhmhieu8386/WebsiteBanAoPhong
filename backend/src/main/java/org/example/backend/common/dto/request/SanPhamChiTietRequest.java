package org.example.backend.common.dto.request;


import lombok.Data;

@Data
public class SanPhamChiTietRequest {

    private String maSanPhamChiTiet;

    private Integer soLuongTon;

    private Float gia;

    private Boolean trangThai;

    private Long idSanPham;

    private Long idMauSac;

    private Long idKichCo;

}