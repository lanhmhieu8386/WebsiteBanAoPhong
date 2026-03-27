package org.example.backend.common.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThuongHieuResponse {

    private Long id;

    private String maThuongHieu;

    private String tenThuongHieu;

    private String moTa;

    private Boolean trangThai;

}