package org.example.backend.customer.service;

import org.example.backend.common.dto.request.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);
}
