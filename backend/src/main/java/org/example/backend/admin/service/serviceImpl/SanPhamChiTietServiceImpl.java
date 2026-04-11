package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.SanPhamChiTietService;
import org.example.backend.common.dto.request.SanPhamChiTietRequest;
import org.example.backend.common.dto.response.SanPhamChiTietResponse;
import org.example.backend.common.entity.KichCo;
import org.example.backend.common.entity.MauSac;
import org.example.backend.common.entity.SanPham;
import org.example.backend.common.entity.SanPhamChiTiet;
import org.example.backend.common.repository.KichCoRepository;
import org.example.backend.common.repository.MauSacRepository;
import org.example.backend.common.repository.SanPhamChiTietRepository;
import org.example.backend.common.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private KichCoRepository kichCoRepository;

    @Override
    public List<SanPhamChiTietResponse> getById(Long id) {
        return sanPhamChiTietRepository.getChiTietBySanPhamId(id);
    }

    @Override
    public SanPhamChiTietResponse getDetailById(Long id) {
        return sanPhamChiTietRepository.getDetailById(id).orElse(null);
    }

    @Override
    public void add(SanPhamChiTietRequest request) {
        validateTrung(request, null);

        SanPham sanPham = sanPhamRepository.findById(request.getIdSanPham())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        MauSac mauSac = mauSacRepository.findById(request.getIdMauSac())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc"));

        KichCo kichCo = kichCoRepository.findById(request.getIdKichCo())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ"));

        SanPhamChiTiet sanPhamChiTiet = SanPhamChiTiet.builder()
                .maSanPhamChiTiet(request.getMaSanPhamChiTiet())
                .soLuongTon(request.getSoLuongTon() != null ? request.getSoLuongTon().longValue() : 0L)
                .gia(request.getGia() != null ? request.getGia() : java.math.BigDecimal.ZERO)
                .ngayTao(new Date())
                .trangThai(request.getTrangThai() != null ? request.getTrangThai() : true)
                .sanPham(sanPham)
                .mauSac(mauSac)
                .kichCo(kichCo)
                .build();

        sanPhamChiTietRepository.save(sanPhamChiTiet);
    }

    @Override
    public void update(Long id, SanPhamChiTietRequest request) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findByIdCustom(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết"));

        validateTrung(request, id);

        SanPham sanPham = sanPhamRepository.findById(request.getIdSanPham())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        MauSac mauSac = mauSacRepository.findById(request.getIdMauSac())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc"));

        KichCo kichCo = kichCoRepository.findById(request.getIdKichCo())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ"));

        sanPhamChiTiet.setMaSanPhamChiTiet(request.getMaSanPhamChiTiet());
        sanPhamChiTiet.setSoLuongTon(request.getSoLuongTon() != null ? request.getSoLuongTon().longValue() : 0L);
        sanPhamChiTiet.setGia(request.getGia() != null ? request.getGia() : java.math.BigDecimal.ZERO);
        sanPhamChiTiet.setTrangThai(request.getTrangThai());
        sanPhamChiTiet.setSanPham(sanPham);
        sanPhamChiTiet.setMauSac(mauSac);
        sanPhamChiTiet.setKichCo(kichCo);

        sanPhamChiTietRepository.save(sanPhamChiTiet);
    }

    @Override
    public void delete(Long id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findByIdCustom(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết"));

        sanPhamChiTiet.setTrangThai(false);
        sanPhamChiTietRepository.save(sanPhamChiTiet);
    }

    private void validateTrung(SanPhamChiTietRequest request, Long id) {
        if (sanPhamChiTietRepository.existsByMaSanPhamChiTiet(request.getMaSanPhamChiTiet(), id)) {
            throw new RuntimeException("Mã sản phẩm chi tiết đã tồn tại");
        }
    }
}