package org.example.backend.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Integer id;
    private String maKhachHang;
    private String tenKhachHang;
    private LocalDateTime ngaySinh;
    private String soDienThoai;
    private String email;
}
