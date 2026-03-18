package org.example.backend.admin.service;

import org.example.backend.common.dto.response.DanhMucResponse;
import org.example.backend.common.dto.response.SanPhamChiTietResponse;
import org.example.backend.common.entity.DanhMuc;

import java.util.List;

public interface DanhMucService {

    List<DanhMuc> getALL();

    DanhMuc getById(Long id);
}
