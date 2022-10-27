package com.emse.spring.faircorp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @Secured("ROLE_ADMIN") // (1)
    @GetMapping(path = "/users")
    public ResponseEntity<String> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUsername());
    }

    @GetMapping(path = "/user")
    public String findUserName(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }
}
