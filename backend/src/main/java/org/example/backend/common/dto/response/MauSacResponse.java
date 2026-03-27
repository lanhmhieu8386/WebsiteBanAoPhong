package org.example.backend.common.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MauSacResponse {

    private Long id;

    private String maMauSac;

    private String tenMauSac;

}