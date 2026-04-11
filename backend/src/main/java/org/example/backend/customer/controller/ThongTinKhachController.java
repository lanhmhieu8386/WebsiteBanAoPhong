package org.example.backend.customer.controller;

import org.example.backend.customer.dto.request.CustomerUpdateRequest;
import org.example.backend.customer.dto.response.CustomerResponse;
import org.example.backend.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khach-hang")
public class ThongTinKhachController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/thong-tin")
    public CustomerResponse getMyInfo(@RequestParam String email) {
        return customerService.getMyInfo(email);
    }

    @PutMapping("/cap-nhat")
    public CustomerResponse updateMyInfo(@RequestBody CustomerUpdateRequest request,
                                         @RequestParam String email) {
        return customerService.updateMyInfo(email, request);
    }
}
