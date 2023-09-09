package com.oauth2easy.api.admin.domain.user;


import com.oauth2easy.api.admin.domain.user.dto.UserRequest;
import com.oauth2easy.api.admin.domain.user.dto.UserResponse;
import com.oauth2easy.api.admin.domain.user.dto.UserUpdateRequest;
import com.oauth2easy.api.admin.exceptions.ResourceNotFoundException;
import com.oauth2easy.api.admin.security.annotations.CanReadUsers;
import com.oauth2easy.api.admin.security.annotations.CanWriteUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @CanReadUsers
    public Page<UserResponse> findAll(Pageable pageable) {
        final Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        final var userResponses = userEntityPage.getContent()
                .stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());
        return new PageImpl<>(userResponses, pageable, userEntityPage.getTotalElements());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CanWriteUsers
    public UserResponse create(@RequestBody @Valid UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return UserResponse.of(userRepository.save(userRequest.toEntity()));
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return UserResponse.of(userRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CanWriteUsers
    public UserResponse update(@PathVariable Long id,
                               @RequestBody UserUpdateRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        request.update(user);
        userRepository.save(user);
        return UserResponse.of(user);
    }
}
