package com.oauth2easy.api.admin.domain.client.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ClientRequest {
    @NotNull
    private String clientId;
    @NotNull
    private List<String> redirectUris;
    @NotNull
    private List<String> scopes;
}
