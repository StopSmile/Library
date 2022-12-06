package com.example.service;

import com.example.entity.User;
import com.example.exceptions.UserNotFoundException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User saveOneUser(User user){
        return this.repository.save(user);
    }

    public User findOneUser(long userId){
        boolean checkUser = repository.existsById(userId);
        if (checkUser){
            this.repository.findById(userId);
        }
        throw new UserNotFoundException(userId);
    }

    public List<User> findAllUser() {
        return IterableUtils.toList(this.repository.findAll())
                .stream()
                .sorted(Comparator.comparingLong(User::getId))
                .collect(Collectors.toList());
    }

    public void deleteOneUser(long userId) {
        boolean checkUser = repository.existsById(userId);
        if (checkUser){
            this.repository.deleteById(userId);
        }
        throw new UserNotFoundException(userId);
    }
}
