package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.SanPhamChiTietService;
import org.example.backend.common.dto.response.SanPhamChiTietResponse;
import org.example.backend.common.repository.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Override
    public List<SanPhamChiTietResponse> getById(Long id) {
        return sanPhamChiTietRepository.getChiTietBySanPhamId(id);
    }
}
