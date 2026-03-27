package org.example.backend.common.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class HinhAnhResponse {

    private Long id;

    private String urlAnh;

    private Date ngayTao;

}