package com.example.Library.services.impl;

import com.example.Library.model.User;
import com.example.Library.repositories.UserRepository;
import com.example.Library.security.SecurityUser;
import com.example.Library.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsServiceImpl")
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            return SecurityUser.fromUser(user.get());
        }
        log.error("User doesn't exists");
        throw new UsernameNotFoundException("User doesn't exists");
    }
}
