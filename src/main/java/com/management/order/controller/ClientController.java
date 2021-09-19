package com.management.order.controller;

import com.management.order.model.Client;
import com.management.order.repository.ClientRepository;
import com.management.order.security.service.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
@Slf4j
@AllArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    @GetMapping
    public ResponseEntity<List<Client>> getAllByUser(@AuthenticationPrincipal UserDetailsImpl userDetail) {
        log.info("Client List called");
        try {
            List<Client> clients = new ArrayList<>();

            clientRepository.findAllByUserIdAndDeletedFalse(userDetail.getId()).forEach(clients::add);

            if(clients.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Client> createClient(@RequestBody Client client, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Client Create called");
        try {
            Client _client = clientRepository
                    .save(Client.builder()
                            .name(client.getName())
                            .user(userDetails.getUser())
                            .address(client.getAddress())
                            .manager(client.getManager())
                            .tel(client.getTel())
                            .build());
            return new ResponseEntity<>(_client, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Client> updateClient(@RequestBody Client client, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Client Update called");
        try {
            Optional<Client> clientOptional = clientRepository.findById(client.getId());
            if (clientOptional.isPresent() && clientOptional.get().getUser().getId() == userDetails.getId()) {
                Client _client = clientOptional.get();
                _client.setName(client.getName());
                _client.setAddress(client.getAddress());
                _client.setManager(client.getManager());
                _client.setTel(client.getTel());
                _client.setBookmarked(client.isBookmarked());
                return new ResponseEntity<>(clientRepository.save(_client), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Client Delete called");
        try {
            Optional<Client> clientOptional = clientRepository.findById(id);
            if (clientOptional.isPresent() && clientOptional.get().getUser().getId() == userDetails.getId()) {
                Client _client = clientOptional.get();
                _client.setDeleted(true);
                clientRepository.save(_client);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
