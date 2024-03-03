package com.ukukhula.bursaryapi.dto;


import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class JwtResponseDTO {

    private final String accessToken;

}
