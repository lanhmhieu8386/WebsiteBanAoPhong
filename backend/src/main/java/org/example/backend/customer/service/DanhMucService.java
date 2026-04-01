package org.example.backend.customer.service;

import org.example.backend.customer.entity.DanhMucCustomer;

import java.util.List;

public interface DanhMucService {

    List<DanhMucCustomer> findAllDanhMuc();
}
