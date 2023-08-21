package com.oauth2easy.api.admin.domain.user.dto;

import com.oauth2easy.api.admin.domain.user.UserEntity;

import javax.validation.constraints.NotBlank;

public class MyUserUpdateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void update(UserEntity currentUser) {
        currentUser.setEmail(this.email);
        currentUser.setName(this.name);
    }
}
