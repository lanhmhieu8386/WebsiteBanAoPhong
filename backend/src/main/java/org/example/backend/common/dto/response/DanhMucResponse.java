package org.example.backend.common.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DanhMucResponse {

    private Long id;

    private String maDanhMuc;

    private String tenDanhMuc;

    private String moTa;

    private Boolean trangThai;

    private Date ngayTao;

    private Date ngayCapNhat;

}