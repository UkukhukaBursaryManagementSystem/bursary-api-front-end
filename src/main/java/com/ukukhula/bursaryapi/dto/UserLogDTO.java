package com.ukukhula.bursaryapi.dto;


import lombok.Data;

@Data
public class UserLogDTO {

    private final int userId;
    private final String userName;
    private final String userAccessToken;

    public UserLogDTO(int userId, String userName, String userAccessToken)
    {
        this.userId = userId;
        this.userName = userName;
        this.userAccessToken = userAccessToken;
    }
}
