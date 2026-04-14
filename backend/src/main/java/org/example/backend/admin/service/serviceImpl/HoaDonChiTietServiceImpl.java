package org.example.backend.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.backend.admin.service.HoaDonChiTietService;
import org.example.backend.common.dto.response.HoaDonChiTietResponse;
import org.example.backend.common.repository.HoaDonChiTietRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    private final HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public List<HoaDonChiTietResponse> getDanhSachByHoaDonId(Integer idHoaDon) {
        return hoaDonChiTietRepository.getDanhSachChiTietByHoaDonId(idHoaDon);
    }

    @Override
    public HoaDonChiTietResponse getDetailById(Integer id) {
        HoaDonChiTietResponse response = hoaDonChiTietRepository.getDetailById(id);
        if (response == null) {
            throw new RuntimeException("Không tìm thấy hóa đơn chi tiết với id = " + id);
        }
        return response;
    }
}