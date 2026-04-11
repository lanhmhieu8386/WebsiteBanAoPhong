package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.HinhAnhService;
import org.example.backend.common.dto.request.HinhAnhRequest;
import org.example.backend.common.dto.response.HinhAnhResponse;
import org.example.backend.common.entity.HinhAnh;
import org.example.backend.common.repository.HinhAnhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HinhAnhServiceImpl implements HinhAnhService {

    @Autowired
    private HinhAnhRepository hinhAnhRepository;

    @Override
    public List<HinhAnhResponse> getAll() {
        List<HinhAnh> list = hinhAnhRepository.findAll();

        return list.stream().map(item -> HinhAnhResponse.builder()
                .id(item.getId())
                .urlAnh(item.getUrlAnh())
                .ngayTao(item.getNgayTao())
                .build()
        ).toList();
    }

    @Override
    public HinhAnhResponse getById(Long id) {
        HinhAnh hinhAnh = hinhAnhRepository.findById(id).orElse(null);

        if (hinhAnh == null) {
            return null;
        }

        return HinhAnhResponse.builder()
                .id(hinhAnh.getId())
                .urlAnh(hinhAnh.getUrlAnh())
                .ngayTao(hinhAnh.getNgayTao())
                .build();
    }

    @Override
    public void add(HinhAnhRequest request) {
        HinhAnh hinhAnh = HinhAnh.builder()
                .urlAnh(request.getUrlAnh())
                .ngayTao(new Date())
                .build();

        hinhAnhRepository.save(hinhAnh);
    }

    @Override
    public void update(Long id, HinhAnhRequest request) {
        HinhAnh hinhAnh = hinhAnhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hình ảnh"));

        hinhAnh.setUrlAnh(request.getUrlAnh());

        hinhAnhRepository.save(hinhAnh);
    }

    @Override
    public void delete(Long id) {
        HinhAnh hinhAnh = hinhAnhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hình ảnh"));

        hinhAnhRepository.delete(hinhAnh);
    }
}