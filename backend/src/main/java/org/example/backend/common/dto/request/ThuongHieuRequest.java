package org.example.backend.common.dto.request;

import lombok.Data;

@Data
public class ThuongHieuRequest {

    private String maThuongHieu;

    private String tenThuongHieu;

    private String moTa;

    private Boolean trangThai;

}
