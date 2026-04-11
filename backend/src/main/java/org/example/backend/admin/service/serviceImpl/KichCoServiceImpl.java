package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.KichCoService;
import org.example.backend.common.dto.request.KichCoRequest;
import org.example.backend.common.dto.response.KichCoResponse;
import org.example.backend.common.entity.KichCo;
import org.example.backend.common.repository.KichCoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KichCoServiceImpl implements KichCoService {

    @Autowired
    private KichCoRepository kichCoRepository;

    @Override
    public List<KichCoResponse> getAll() {
        List<KichCo> list = kichCoRepository.findAll();

        return list.stream().map(item -> KichCoResponse.builder()
                .id(item.getId())
                .maKichCo(item.getMaKichCo())
                .tenKichCo(item.getTenKichCo())
                .build()
        ).toList();
    }

    @Override
    public KichCoResponse getById(Long id) {
        KichCo kichCo = kichCoRepository.findById(id).orElse(null);

        if (kichCo == null) {
            return null;
        }

        return KichCoResponse.builder()
                .id(kichCo.getId())
                .maKichCo(kichCo.getMaKichCo())
                .tenKichCo(kichCo.getTenKichCo())
                .build();
    }

    @Override
    public void add(KichCoRequest request) {
        validateTrung(request, null);

        KichCo kichCo = KichCo.builder()
                .maKichCo(request.getMaKichCo())
                .tenKichCo(request.getTenKichCo())
                .build();

        kichCoRepository.save(kichCo);
    }

    @Override
    public void update(Long id, KichCoRequest request) {
        KichCo kichCo = kichCoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ"));

        validateTrung(request, id);

        kichCo.setMaKichCo(request.getMaKichCo());
        kichCo.setTenKichCo(request.getTenKichCo());

        kichCoRepository.save(kichCo);
    }

    @Override
    public void delete(Long id) {
        KichCo kichCo = kichCoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ"));

        kichCoRepository.delete(kichCo);
    }

    private void validateTrung(KichCoRequest request, Long id) {
        if (kichCoRepository.existsByMaKichCo(request.getMaKichCo(), id)) {
            throw new RuntimeException("Mã kích cỡ đã tồn tại");
        }
    }
}