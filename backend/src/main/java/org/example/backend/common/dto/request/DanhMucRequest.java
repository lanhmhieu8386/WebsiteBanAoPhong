package org.example.backend.common.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class DanhMucRequest {

    private String maDanhMuc;

    private String tenDanhMuc;

    private String moTa;

    private Boolean trangThai;

    private Date ngayTao;

    private Date ngayCapNhat;

}