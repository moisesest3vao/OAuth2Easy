package com.oauth2easy.api.admin.domain.client;

import com.oauth2easy.api.admin.domain.client.dto.ClientRequest;
import com.oauth2easy.api.admin.domain.client.dto.ClientResponse;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;
    private JdbcRegisteredClientRepository clientRepository;
    private JdbcOperations jdbcOperations;
    private RowMapper<RegisteredClient> registeredClientRowMapper;

    public ClientService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, JdbcOperations jdbcOperations) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        this.jdbcOperations = jdbcOperations;
        this.registeredClientRowMapper = new JdbcRegisteredClientRepository.RegisteredClientRowMapper();
    }

    public ClientResponse save(ClientRequest request) {
        String clientId = request.getClientId();

        if (clientRepository.findByClientId(clientId) != null) return null;

        String clientSecret = UUID.randomUUID().toString().substring(0, 20);

        RegisteredClient.Builder builder = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(passwordEncoder.encode(clientSecret))

                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .clientAuthenticationMethod(ClientAuthenticationMethod.PRIVATE_KEY_JWT)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)

                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)

                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(15))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .reuseRefreshTokens(false)
                        .build()
                )

                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build()
                );

        for (String scope : request.getScopes()) {
            builder.scope(scope);
        }

        for (String redirectUri : request.getRedirectUris()) {
            builder.redirectUri(redirectUri);
        }

        RegisteredClient registeredClient = builder.build();

        clientRepository.save(registeredClient);

        return new ClientResponse(registeredClient, clientSecret);
    }

    public List<ClientResponse> findAll() {
        List<RegisteredClient> query = this.jdbcOperations.query("SELECT id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings,token_settings FROM oauth2_registered_client", this.registeredClientRowMapper, null);

        if (query != null && !query.isEmpty()) {
            return query.stream().map(item -> new ClientResponse(item, item.getClientSecret())).collect(Collectors.toList());
        }

        return null;
    }

    public ClientResponse findById(String id) {
        RegisteredClient byId = this.clientRepository.findByClientId(id);
        if (byId == null) return null;
        return new ClientResponse(byId, byId.getClientSecret());
    }
}
