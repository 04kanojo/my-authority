package com.kanojo.modules.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserParam implements Serializable {
    private String username;

    private String password;
}

