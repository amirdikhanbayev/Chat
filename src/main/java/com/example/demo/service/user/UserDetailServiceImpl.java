package com.example.demo.service.user;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = usersRepository.findByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        });
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword()
                , authorities);
//        com.example.demo.model.Users users =usersRepository.findByUsername(login).orElseThrow(
//                ()->new UsernameNotFoundException("User not found"));
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        users.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        });
//        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(),
//              authorities);
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        String currentUserUsername = authentication.getName();
//        if (!login.equals(currentUserUsername)) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        return new org.springframework.security.core.userdetails.User(
//                login,
//                "",
//                authorities
//        );
//        com.example.demo.model.Users users = usersRepository.findByUsername(login)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        users.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole())));
//
//        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(), authorities);

    }
    }



