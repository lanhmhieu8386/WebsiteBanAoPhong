package org.example.backend.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.backend.admin.service.SanPhamService;
import org.example.backend.common.dto.request.SanPhamRequest;
import org.example.backend.common.dto.response.SanPhamDetailResponse;
import org.example.backend.common.dto.response.SanPhamResponse;
import org.example.backend.common.entity.SanPham;
import org.example.backend.common.repository.ChatLieuRepository;
import org.example.backend.common.repository.DanhMucRepository;
import org.example.backend.common.repository.SanPhamRepository;
import org.example.backend.common.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private  SanPhamRepository sanPhamRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Override
    public List<SanPhamResponse> getAll() {
        return sanPhamRepository.getAllSanPham(); // 👈 CHUẨN
    }

    @Override
    public SanPhamDetailResponse getById(Long id) {
        return sanPhamRepository.getById(id);
    }

    @Override
    public void add(SanPhamRequest request) {

        SanPham sp = SanPham.builder()
                .maSanPham(request.getMaSanPham())
                .tenSanPham(request.getTenSanPham())
                .moTa(request.getMoTa())
                .trangThai(true)
                .ngayTao(new Date())
                .danhMuc(danhMucRepository.findById(request.getIdDanhMuc()).orElse(null))
                .thuongHieu(thuongHieuRepository.findById(request.getIdThuongHieu()).orElse(null))
                .chatLieu(chatLieuRepository.findById(request.getIdChatLieu()).orElse(null))
                .build();

        sanPhamRepository.save(sp);
    }

    @Override
    public void update(Long id, SanPhamRequest request) {

        SanPham sp = sanPhamRepository.findById(id).orElse(null);

        if (sp != null) {

            sp.setMaSanPham(request.getMaSanPham());
            sp.setTenSanPham(request.getTenSanPham());
            sp.setMoTa(request.getMoTa());
            sp.setTrangThai(request.getTrangThai());

            sp.setDanhMuc(danhMucRepository.findById(request.getIdDanhMuc()).orElse(null));
            sp.setThuongHieu(thuongHieuRepository.findById(request.getIdThuongHieu()).orElse(null));
            sp.setChatLieu(chatLieuRepository.findById(request.getIdChatLieu()).orElse(null));

            sanPhamRepository.save(sp);
        }
    }

    @Override
    public void delete(Long id) {

        SanPham sp = sanPhamRepository.findById(id).orElse(null);

        if (sp != null) {
            sp.setTrangThai(false);
            sanPhamRepository.save(sp);
        }
    }
}
