package com.example.demo.service.get;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.get.GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetServiceImpl implements GetService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return usersRepository.findByUsername(principal.getUsername())
                .orElseThrow(()-> new IllegalArgumentException("User not found"));
    }
}
