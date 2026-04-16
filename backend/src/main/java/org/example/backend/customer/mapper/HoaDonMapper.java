package org.example.backend.customer.mapper;

import org.example.backend.customer.dto.request.HoaDonCustomerRequest;
import org.example.backend.customer.dto.response.HoaDonCustomerResponse;
import org.example.backend.customer.entity.HoaDonCustomer;
import org.example.backend.customer.entity.KhachHangCustomer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class HoaDonMapper {

    public HoaDonCustomer mapToEntity(
            HoaDonCustomerRequest request,
            KhachHangCustomer khachHang,
            String nguoiTao
    ) {
        HoaDonCustomer hd = new HoaDonCustomer();

        hd.setMaHoaDon("HD_" + System.currentTimeMillis());
        hd.setKhachHang(khachHang);
        hd.setTrangThai(request.getTrangThai());
        hd.setTongTien(BigDecimal.ZERO);
        hd.setSoDienThoai(request.getSoDienThoai());
        hd.setDiaChiGiaoHang(request.getDiaChi());
        hd.setNgayTao(new Date());
        hd.setNguoiTao(nguoiTao);

        return hd;
    }

    public HoaDonCustomerResponse mapToResponse(HoaDonCustomer hd) {

        List<HoaDonCustomerResponse.Item> items =
                hd.getHoaDonChiTiets()
                        .stream()
                        .map(ct -> {
                            HoaDonCustomerResponse.Item item = new HoaDonCustomerResponse.Item();

                            item.setIdSanPhamChiTiet(ct.getSanPhamChiTiet().getId());
                            item.setTenSanPham(ct.getSanPhamChiTiet().getSanPham().getTenSanPham());
                            item.setSoLuong(ct.getSoLuong());
                            item.setGia(ct.getGia());
                            item.setThanhTien(
                                    ct.getGia().multiply(BigDecimal.valueOf(ct.getSoLuong()))
                            );

                            return item;
                        })
                        .toList();

        return HoaDonCustomerResponse.builder()
                .id(hd.getId())
                .maHoaDon(hd.getMaHoaDon())
                .idKhachHang(hd.getKhachHang() != null ? hd.getKhachHang().getId() : null)
                .tenKhachHang(hd.getKhachHang() != null ? hd.getKhachHang().getTenKhachHang() : null)
                .trangThai(hd.getTrangThai())
                .tongTien(hd.getTongTien())
                .diaChiGiaoHang(hd.getDiaChiGiaoHang())
                .items(items)
                .build();
    }
}
