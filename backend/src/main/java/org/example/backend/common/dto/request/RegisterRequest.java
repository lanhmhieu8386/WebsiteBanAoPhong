package org.example.backend.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate ngaySinh;

}
