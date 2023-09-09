package com.oauth2easy.api.admin.domain.client.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Set;

@Getter
@Setter
public class ClientResponse {
    private String id;
    private String clientId;
    private String clientSecret;
    private Set<String> redirectUris;
    private Set<String> scopes;

    public ClientResponse(RegisteredClient registeredClient, String clientSecret) {
        this.id = registeredClient.getId();
        this.clientId = registeredClient.getClientId();
        this.clientSecret = clientSecret;
        this.redirectUris = registeredClient.getRedirectUris();
        this.scopes = registeredClient.getScopes();
    }
}
