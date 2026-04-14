package org.example.backend.customer.controller;

import org.example.backend.customer.dto.request.HoaDonCustomerRequest;
import org.example.backend.customer.dto.response.HoaDonCustomerResponse;
import org.example.backend.customer.service.HoaDonCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/hoa-don")
public class HoaDonCustomerController {

    @Autowired
    private HoaDonCustomerService hoaDonCustomerService;

    // ===== CHECKOUT =====
    @PostMapping("/checkout")
    public ResponseEntity<HoaDonCustomerResponse> checkout(
            @RequestBody HoaDonCustomerRequest request
    ) {
        return ResponseEntity.ok(
                hoaDonCustomerService.checkout(request)
        );
    }

    @GetMapping("/my-order")
    public List<HoaDonCustomerResponse> getMyOrder() {
        return hoaDonCustomerService.getMyOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(hoaDonCustomerService.getOrderDetail(id));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(hoaDonCustomerService.cancelOrder(id));
    }
}