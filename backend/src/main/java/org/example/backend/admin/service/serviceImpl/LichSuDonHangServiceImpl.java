package org.example.backend.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.backend.admin.service.LichSuDonHangService;
import org.example.backend.common.dto.response.LichSuDonHangResponse;
import org.example.backend.common.repository.LichSuDonHangRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LichSuDonHangServiceImpl implements LichSuDonHangService {

    private final LichSuDonHangRepository lichSuDonHangRepository;

    @Override
    public List<LichSuDonHangResponse> getAll() {
        return lichSuDonHangRepository.getAllResponse();
    }

    @Override
    public LichSuDonHangResponse getDetailById(Integer id) {
        LichSuDonHangResponse response = lichSuDonHangRepository.getDetailById(id);
        if (response == null) {
            throw new RuntimeException("Không tìm thấy lịch sử đơn hàng với id = " + id);
        }
        return response;
    }

    @Override
    public List<LichSuDonHangResponse> getByHoaDonId(Integer idHoaDon) {
        return lichSuDonHangRepository.getByHoaDonId(idHoaDon);
    }
}