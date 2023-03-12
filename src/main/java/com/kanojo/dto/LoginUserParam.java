package com.kanojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserParam implements Serializable {
    private String username;

    private String password;
}

