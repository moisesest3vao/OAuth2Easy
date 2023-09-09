package com.oauth2easy.api.admin.domain.client;

import com.oauth2easy.api.admin.domain.client.dto.ClientRequest;
import com.oauth2easy.api.admin.domain.client.dto.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody @Valid ClientRequest request) {
        ClientResponse response = this.clientService.save(request);
        return response != null ? ResponseEntity.status(HttpStatus.CREATED).body(response) : ResponseEntity.badRequest().body("clientId already exists.");
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<ClientResponse> response = this.clientService.findAll();
        return response != null ? ResponseEntity.status(HttpStatus.OK).body(response) : ResponseEntity.badRequest().body("No results returned.");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        ClientResponse response = this.clientService.findById(id);
        return response != null ? ResponseEntity.status(HttpStatus.OK).body(response) : ResponseEntity.badRequest().body("No results returned.");
    }
}
