package com.example.Library.controller;

import com.example.Library.dto.AuthenticationRequestDTO;
import com.example.Library.dto.AuthenticationResponseDTO;
import com.example.Library.services.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@Log4j2
public class AuthenticationRestController {
    private final AuthenticationService authenticationService;
    public AuthenticationRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public AuthenticationResponseDTO authenticate(@RequestBody AuthenticationRequestDTO request) {
        log.info("Executing method POST /api/v1/auth/login");
        return authenticationService.authenticate(request);
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Executing method POST /api/v1/auth/logout");
        authenticationService.logout(request,response);
    }
}
