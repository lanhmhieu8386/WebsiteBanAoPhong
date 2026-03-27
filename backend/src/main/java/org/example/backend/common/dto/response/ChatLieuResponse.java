package org.example.backend.common.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatLieuResponse {

    private Long id;

    private String maChatLieu;

    private String tenChatLieu;

    private String moTa;

    private Boolean trangThai;

}
