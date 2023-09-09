package com.oauth2easy.api.admin.domain.user.dto;

import javax.validation.constraints.NotBlank;

public class MyUserUpdatePasswordRequest {

    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
