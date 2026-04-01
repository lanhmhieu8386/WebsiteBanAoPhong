package org.example.backend.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {

    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private LocalDateTime ngaySinh;
}
