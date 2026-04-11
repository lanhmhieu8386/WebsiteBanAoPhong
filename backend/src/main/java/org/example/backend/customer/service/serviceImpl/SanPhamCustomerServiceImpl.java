package org.example.backend.customer.service.serviceImpl;

import org.example.backend.customer.dto.response.SanPhamChiTietResponse;
import org.example.backend.customer.dto.response.SanPhamResponse;
import org.example.backend.customer.entity.SanPhamCustomer;
import org.example.backend.customer.repository.SanPhamCustomerRepository;
import org.example.backend.customer.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SanPhamCustomerServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamCustomerRepository sanPhamRepository;

    @Override
    public List<SanPhamResponse> findAll() {
        return sanPhamRepository.findAllWithChiTiet().stream().map(sp -> {

            // map chi tiết
            List<SanPhamChiTietResponse> chiTietList = sp.getSanPhamChiTietList()
                    .stream()
                    .map(ct -> SanPhamChiTietResponse.builder()
                            .id(ct.getId())
                            .maSanPhamChiTiet(ct.getMaSanPhamChiTiet())
                            .tenMauSac(ct.getMauSac().getTenMauSac())
                            .tenKichCo(ct.getKichCo().getTenKichCo())
                            .soLuongTon(ct.getSoLuongTon())
                            .gia(ct.getGia())
                            .build()
                    ).toList();

            // tính giá min
            BigDecimal giaMin = chiTietList.stream()
                    .map(SanPhamChiTietResponse::getGia)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            return SanPhamResponse.builder()
                    .id(sp.getId())
                    .maSanPham(sp.getMaSanPham())
                    .tenSanPham(sp.getTenSanPham())
                    .moTa(sp.getMoTa())

                    .tenDanhMuc(sp.getDanhMuc().getTenDanhMuc())
                    .tenThuongHieu(sp.getThuongHieu().getTenThuongHieu())
                    .tenChatLieu(sp.getChatLieu().getTenChatLieu())

                    .giaMin(giaMin)
                    .chiTietList(chiTietList)
                    .build();
        }).toList();
    }

    @Override
    public List<SanPhamResponse> findByIdDanhMuc(Long idDanhMuc) {
        return sanPhamRepository.findByDanhMucWithChiTiet(idDanhMuc)
                .stream()
                .map(sp -> {

                    List<SanPhamChiTietResponse> chiTietList = sp.getSanPhamChiTietList()
                            .stream()
                            .map(ct -> SanPhamChiTietResponse.builder()
                                    .id(ct.getId())
                                    .maSanPhamChiTiet(ct.getMaSanPhamChiTiet())
                                    .tenMauSac(ct.getMauSac().getTenMauSac())
                                    .tenKichCo(ct.getKichCo().getTenKichCo())
                                    .soLuongTon(ct.getSoLuongTon())
                                    .gia(ct.getGia())
                                    .build()
                            ).toList();

                    // giá nhỏ nhất
                    BigDecimal giaMin = chiTietList.stream()
                            .map(SanPhamChiTietResponse::getGia)
                            .min(BigDecimal::compareTo)
                            .orElse(BigDecimal.ZERO);

                    return SanPhamResponse.builder()
                            .id(sp.getId())
                            .maSanPham(sp.getMaSanPham())
                            .tenSanPham(sp.getTenSanPham())
                            .moTa(sp.getMoTa())

                            .tenDanhMuc(sp.getDanhMuc().getTenDanhMuc())
                            .tenThuongHieu(sp.getThuongHieu().getTenThuongHieu())
                            .tenChatLieu(sp.getChatLieu().getTenChatLieu())

                            .giaMin(giaMin)
                            .chiTietList(chiTietList)
                            .build();
                }).toList();
    }

    @Override
    public SanPhamResponse findById(Long id) {
        SanPhamCustomer sp = sanPhamRepository.findByIdWithChiTiet(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy"));

        return mapToResponse(sp);
    }

    private SanPhamResponse mapToResponse(SanPhamCustomer sp) {

        // map chi tiết
        List<SanPhamChiTietResponse> chiTietList = sp.getSanPhamChiTietList()
                .stream()
                .map(ct -> SanPhamChiTietResponse.builder()
                        .id(ct.getId())
                        .maSanPhamChiTiet(ct.getMaSanPhamChiTiet())
                        .tenMauSac(ct.getMauSac().getTenMauSac())
                        .tenKichCo(ct.getKichCo().getTenKichCo())
                        .soLuongTon(ct.getSoLuongTon())
                        .gia(ct.getGia())
                        .build()
                ).toList();

        // tính giá nhỏ nhất
        BigDecimal giaMin = chiTietList.stream()
                .map(SanPhamChiTietResponse::getGia)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        return SanPhamResponse.builder()
                .id(sp.getId())
                .maSanPham(sp.getMaSanPham())
                .tenSanPham(sp.getTenSanPham())
                .moTa(sp.getMoTa())

                .tenDanhMuc(sp.getDanhMuc().getTenDanhMuc())
                .tenThuongHieu(sp.getThuongHieu().getTenThuongHieu())
                .tenChatLieu(sp.getChatLieu().getTenChatLieu())

                .giaMin(giaMin)
                .chiTietList(chiTietList)
                .build();
    }
}
