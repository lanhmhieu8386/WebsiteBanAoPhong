package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.ThuongHieuService;
import org.example.backend.common.dto.request.ThuongHieuRequest;
import org.example.backend.common.dto.response.ThuongHieuResponse;
import org.example.backend.common.entity.ThuongHieu;
import org.example.backend.common.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Override
    public List<ThuongHieuResponse> getAll() {
        List<ThuongHieu> list = (List<ThuongHieu>) thuongHieuRepository.findAll();

        return list.stream().map(item -> ThuongHieuResponse.builder()
                .id(item.getId())
                .maThuongHieu(item.getMaThuongHieu())
                .tenThuongHieu(item.getTenThuongHieu())
                .moTa(item.getMoTa())
                .trangThai(item.getTrangThai())
                .build()
        ).toList();
    }

    @Override
    public ThuongHieuResponse getById(Long id) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id).orElse(null);

        if (thuongHieu == null) {
            return null;
        }

        return ThuongHieuResponse.builder()
                .id(thuongHieu.getId())
                .maThuongHieu(thuongHieu.getMaThuongHieu())
                .tenThuongHieu(thuongHieu.getTenThuongHieu())
                .moTa(thuongHieu.getMoTa())
                .trangThai(thuongHieu.getTrangThai())
                .build();
    }

    @Override
    public void add(ThuongHieuRequest request) {
        ThuongHieu thuongHieu = ThuongHieu.builder()
                .maThuongHieu(request.getMaThuongHieu())
                .tenThuongHieu(request.getTenThuongHieu())
                .moTa(request.getMoTa())
                .trangThai(request.getTrangThai() != null ? request.getTrangThai() : true)
                .build();

        thuongHieuRepository.save(thuongHieu);
    }

    @Override
    public void update(Long id, ThuongHieuRequest request) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id).orElse(null);

        if (thuongHieu != null) {
            thuongHieu.setMaThuongHieu(request.getMaThuongHieu());
            thuongHieu.setTenThuongHieu(request.getTenThuongHieu());
            thuongHieu.setMoTa(request.getMoTa());
            thuongHieu.setTrangThai(request.getTrangThai());

            thuongHieuRepository.save(thuongHieu);
        }
    }

    @Override
    public void delete(Long id) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id).orElse(null);

        if (thuongHieu != null) {
            thuongHieu.setTrangThai(false);
            thuongHieuRepository.save(thuongHieu);
        }
    }
}