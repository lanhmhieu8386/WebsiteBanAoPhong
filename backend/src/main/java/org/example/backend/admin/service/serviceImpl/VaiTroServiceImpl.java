package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.VaiTroService;
import org.example.backend.common.entity.VaiTro;
import org.example.backend.common.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaiTroServiceImpl implements VaiTroService {

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Override
    public List<VaiTro> getAll() {
        return vaiTroRepository.findAll();
    }
}
