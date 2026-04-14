package org.example.backend.customer.service;

import org.example.backend.customer.dto.request.HoaDonCustomerRequest;
import org.example.backend.customer.dto.response.HoaDonCustomerResponse;

import java.util.List;

public interface HoaDonCustomerService {
    HoaDonCustomerResponse checkout(HoaDonCustomerRequest request);

    List<HoaDonCustomerResponse> getMyOrders();

    HoaDonCustomerResponse getOrderDetail(Long orderId);

    HoaDonCustomerResponse cancelOrder(Long orderId);
}
