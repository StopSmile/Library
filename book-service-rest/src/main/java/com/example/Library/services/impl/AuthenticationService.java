package com.example.Library.services.impl;

import com.example.Library.dto.UserDTO;
import com.example.Library.dto.AuthenticationRequestDTO;
import com.example.Library.dto.AuthenticationResponseDTO;
import com.example.Library.exceptions.InvalidEmailOrPassword;
import com.example.Library.security.JwtTokenProvider;
import com.example.Library.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Log4j2
public class AuthenticationService {
    public AuthenticationService(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    public AuthenticationResponseDTO authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            log.error("Invalid email/password combination", e);
            throw new InvalidEmailOrPassword(request.getEmail(), request.getPassword());
        }
        Optional<UserDTO> user = userService.findByEmail(request.getEmail());
        if (user.isPresent()) {
            String token = jwtTokenProvider.createToken(request.getEmail(), user.get().getRole());
            return new AuthenticationResponseDTO(request.getEmail(), token);
        }
        log.error("User doesn't exist {}", request.getEmail());
        throw new UsernameNotFoundException("User doesn't exist");
    }
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}