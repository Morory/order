package com.management.order.controller;

import com.management.order.domain.User;
import com.management.order.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ApiController {

    private final UserRepository repository;

    @GetMapping("/list")
    public List<User> hello() {
        log.info("List Called");
        return repository.findAll();
    }
}
