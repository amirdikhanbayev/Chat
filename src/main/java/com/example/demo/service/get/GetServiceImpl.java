package com.example.demo.service.get;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
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
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Users user = usersRepository.findByUsername(username).orElse(null);
            return user;
        }
        return null;
    }
}
