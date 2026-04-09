package org.example.backend.customer.service;

import org.example.backend.customer.dto.request.CustomerUpdateRequest;
import org.example.backend.customer.dto.response.CustomerResponse;

public interface CustomerService {
    CustomerResponse getMyInfo(String email);

    CustomerResponse updateMyInfo(String email, CustomerUpdateRequest request);
}
