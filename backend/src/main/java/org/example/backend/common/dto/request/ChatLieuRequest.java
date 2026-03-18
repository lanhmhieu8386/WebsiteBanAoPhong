package org.example.backend.common.dto.request;


import lombok.Data;

@Data
public class ChatLieuRequest {

    private String maChatLieu;

    private String tenChatLieu;

    private String moTa;

    private Boolean trangThai;

}
