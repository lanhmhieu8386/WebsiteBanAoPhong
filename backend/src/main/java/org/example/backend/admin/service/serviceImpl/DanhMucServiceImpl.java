package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.DanhMucService;
import org.example.backend.common.dto.request.DanhMucRequest;
import org.example.backend.common.dto.response.DanhMucResponse;
import org.example.backend.common.entity.DanhMuc;
import org.example.backend.common.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DanhMucServiceImpl implements DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Override
    public List<DanhMucResponse> getALL() {
        List<DanhMuc> list = danhMucRepository.findAll();

        return list.stream().map(item -> DanhMucResponse.builder()
                .id(item.getId())
                .maDanhMuc(item.getMaDanhMuc())
                .tenDanhMuc(item.getTenDanhMuc())
                .moTa(item.getMoTa())
                .trangThai(item.getTrangThai())
                .ngayTao(item.getNgayTao())
                .ngayCapNhat(item.getNgayCapNhat())
                .build()
        ).toList();
    }

    @Override
    public DanhMucResponse getById(Long id) {
        DanhMuc danhMuc = danhMucRepository.findById(id).orElse(null);

        if (danhMuc == null) {
            return null;
        }

        return DanhMucResponse.builder()
                .id(danhMuc.getId())
                .maDanhMuc(danhMuc.getMaDanhMuc())
                .tenDanhMuc(danhMuc.getTenDanhMuc())
                .moTa(danhMuc.getMoTa())
                .trangThai(danhMuc.getTrangThai())
                .ngayTao(danhMuc.getNgayTao())
                .ngayCapNhat(danhMuc.getNgayCapNhat())
                .build();
    }

    @Override
    public void add(DanhMucRequest request) {
        validateTrung(request, null);

        DanhMuc danhMuc = DanhMuc.builder()
                .maDanhMuc(request.getMaDanhMuc())
                .tenDanhMuc(request.getTenDanhMuc())
                .moTa(request.getMoTa())
                .trangThai(request.getTrangThai() != null ? request.getTrangThai() : true)
                .ngayTao(new Date())
                .ngayCapNhat(new Date())
                .build();

        danhMucRepository.save(danhMuc);
    }

    @Override
    public void update(Long id, DanhMucRequest request) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

        validateTrung(request, id);

        danhMuc.setMaDanhMuc(request.getMaDanhMuc());
        danhMuc.setTenDanhMuc(request.getTenDanhMuc());
        danhMuc.setMoTa(request.getMoTa());
        danhMuc.setTrangThai(request.getTrangThai());
        danhMuc.setNgayCapNhat(new Date());

        danhMucRepository.save(danhMuc);
    }

    @Override
    public void delete(Long id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

        danhMuc.setTrangThai(false);
        danhMuc.setNgayCapNhat(new Date());

        danhMucRepository.save(danhMuc);
    }

    private void validateTrung(DanhMucRequest request, Long id) {
        if (danhMucRepository.existsByMaDanhMuc(request.getMaDanhMuc(), id)) {
            throw new RuntimeException("Mã danh mục đã tồn tại");
        }
    }
}