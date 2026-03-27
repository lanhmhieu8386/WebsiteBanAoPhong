package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.DanhMucService;
import org.example.backend.common.dto.response.DanhMucResponse;
import org.example.backend.common.dto.response.SanPhamChiTietResponse;
import org.example.backend.common.entity.DanhMuc;
import org.example.backend.common.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucServiceImpl implements DanhMucService {
    @Autowired
    private DanhMucRepository danhMucRepository;


    @Override
    public List<DanhMuc> getALL() {
        return (List<DanhMuc>) danhMucRepository.findAll();
    }

    @Override
    public DanhMuc getById(Long id) {
        return danhMucRepository.findById(id).orElse(null);
    }
}
