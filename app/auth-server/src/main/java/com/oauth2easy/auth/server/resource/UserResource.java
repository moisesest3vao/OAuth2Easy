package com.oauth2easy.auth.server.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/users")
public class UserResource {
    @PostMapping
    public String healthCheck(@PathVariable Long id){
        return id.toString();
    }

    @GetMapping
    public String healthCheckf(){
        return "123123131312";
    }
}
