package com.ukukhula.bursaryapi.dto;


import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class JwtResponseDTO {

    private final String accessToken;

}
