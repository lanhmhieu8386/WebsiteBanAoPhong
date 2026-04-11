package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.MauSacService;
import org.example.backend.common.dto.request.MauSacRequest;
import org.example.backend.common.dto.response.MauSacResponse;
import org.example.backend.common.entity.MauSac;
import org.example.backend.common.repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;

    @Override
    public List<MauSacResponse> getAll() {
        List<MauSac> list = mauSacRepository.findAll();

        return list.stream().map(item -> MauSacResponse.builder()
                .id(item.getId())
                .maMauSac(item.getMaMauSac())
                .tenMauSac(item.getTenMauSac())
                .build()
        ).toList();
    }

    @Override
    public MauSacResponse getById(Long id) {
        MauSac mauSac = mauSacRepository.findById(id).orElse(null);

        if (mauSac == null) {
            return null;
        }

        return MauSacResponse.builder()
                .id(mauSac.getId())
                .maMauSac(mauSac.getMaMauSac())
                .tenMauSac(mauSac.getTenMauSac())
                .build();
    }

    @Override
    public void add(MauSacRequest request) {
        validateTrung(request, null);

        MauSac mauSac = MauSac.builder()
                .maMauSac(request.getMaMauSac())
                .tenMauSac(request.getTenMauSac())
                .build();

        mauSacRepository.save(mauSac);
    }

    @Override
    public void update(Long id, MauSacRequest request) {
        MauSac mauSac = mauSacRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc"));

        validateTrung(request, id);

        mauSac.setMaMauSac(request.getMaMauSac());
        mauSac.setTenMauSac(request.getTenMauSac());

        mauSacRepository.save(mauSac);
    }

    @Override
    public void delete(Long id) {
        MauSac mauSac = mauSacRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc"));

        mauSacRepository.delete(mauSac);
    }

    private void validateTrung(MauSacRequest request, Long id) {
        if (mauSacRepository.existsByMaMauSac(request.getMaMauSac(), id)) {
            throw new RuntimeException("Mã màu sắc đã tồn tại");
        }
    }
}