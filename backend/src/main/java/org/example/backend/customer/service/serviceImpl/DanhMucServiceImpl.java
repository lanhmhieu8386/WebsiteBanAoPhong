package org.example.backend.customer.service.serviceImpl;

import org.example.backend.customer.entity.DanhMuc;
import org.example.backend.customer.repository.DanhMucRepository;
import org.example.backend.customer.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucServiceImpl implements DanhMucService {
    @Autowired
    private DanhMucRepository danhMucRepository;


    @Override
    public List<DanhMuc> findAllDanhMuc() {
        return danhMucRepository.findAll();
    }
}
