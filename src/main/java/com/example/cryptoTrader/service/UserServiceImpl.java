package com.example.cryptoTrader.service;

import com.example.cryptoTrader.domain.User;
import com.example.cryptoTrader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Override
    public User getUserById(Long userId) {
        return  userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }
}
