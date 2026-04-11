package org.example.backend.customer.service;

import org.example.backend.customer.dto.request.ThemGioHangRequest;
import org.example.backend.customer.dto.response.GioHangResponse;

public interface GioHangService {
    GioHangResponse themVaoGio(ThemGioHangRequest req);
}
