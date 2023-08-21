package com.oauth2easy.api.admin.domain.user;

import com.oauth2easy.api.admin.domain.user.UserEntity;
import com.oauth2easy.api.admin.domain.user.UserRepository;
import com.oauth2easy.api.admin.domain.user.dto.MyUserRegisterRequest;
import com.oauth2easy.api.admin.domain.user.dto.MyUserUpdatePasswordRequest;
import com.oauth2easy.api.admin.domain.user.dto.MyUserUpdateRequest;
import com.oauth2easy.api.admin.domain.user.dto.UserResponse;
import com.oauth2easy.api.admin.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class MyUserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MyUserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public UserResponse me(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaims().get("sub").toString();

        return userRepository.findByEmail(email)
               .map(UserResponse::of)
               .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal Jwt jwt,
                        @RequestBody MyUserUpdateRequest request) {
        String email = jwt.getClaims().get("sub").toString();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        request.update(user);
        userRepository.save(user);
    }

    @PutMapping("/update-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@AuthenticationPrincipal Jwt jwt,
                        @RequestBody MyUserUpdatePasswordRequest request) {
        String email = jwt.getClaims().get("sub").toString();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid MyUserRegisterRequest request, @AuthenticationPrincipal Jwt jwt){
        UserEntity user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = this.userRepository.save(user);
        return UserResponse.of(user);
    }

}
