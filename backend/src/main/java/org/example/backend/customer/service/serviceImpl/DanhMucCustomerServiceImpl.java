package org.example.backend.customer.service.serviceImpl;

import org.example.backend.customer.entity.DanhMucCustomer;
import org.example.backend.customer.repository.DanhMucCustomerRepository;
import org.example.backend.customer.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucCustomerServiceImpl implements DanhMucService {
    @Autowired
    private DanhMucCustomerRepository danhMucRepository;


    @Override
    public List<DanhMucCustomer> findAllDanhMuc() {
        return danhMucRepository.findAll();
    }
}
