package org.example.backend.common.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KichCoResponse {

    private Long id;

    private String maKichCo;

    private String tenKichCo;

}