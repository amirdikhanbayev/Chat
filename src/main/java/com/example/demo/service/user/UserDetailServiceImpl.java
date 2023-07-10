package com.example.demo.service.user;

import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        com.example.demo.model.Users users =usersRepository.findByUsername(login).orElseThrow(
                ()->new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }
}