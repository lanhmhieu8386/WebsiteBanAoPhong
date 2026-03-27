package org.example.backend.admin.service;

import org.example.backend.common.entity.DanhMuc;
import org.example.backend.common.entity.ThuongHieu;

import java.util.List;

public interface ThuongHieuService {

    List<ThuongHieu> getAll();

    ThuongHieu getById(Long id);
}
