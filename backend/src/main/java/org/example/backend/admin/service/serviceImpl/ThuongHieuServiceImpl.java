package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.ThuongHieuService;
import org.example.backend.common.entity.DanhMuc;
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
    public List<ThuongHieu> getAll() {
        return (List<ThuongHieu>) thuongHieuRepository.findAll();
    }



    @Override
    public ThuongHieu getById(Long id){
        return thuongHieuRepository.findById(id).orElse(null);
    }
}
