package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.service.get.GetService;
import com.example.demo.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GetService getService;

    @GetMapping("/logout")
    public String logout() {
        getService.getCurrentUser().setOnline(false);
        SecurityContextHolder.clearContext();
        return "Logged out";
    }//work

    @PostMapping("/create")
    public Users create(@RequestBody Users users){
        return userService.create(users);
    }//work
    @PatchMapping ("/changeUsername/{id}/{username}")
    public Users changeUsername(@PathVariable Long id, @PathVariable String username){//work
        return userService.changeUsername(id, username);
    }

    @DeleteMapping("/delete/{id}" )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delete(@PathVariable Long id){//work
        return userService.deleteUser(id);
    }

    @GetMapping("/findByUsername/{username}")
    public Optional<Users> findByUsername(@PathVariable String username){//work
        return userService.findByUsername(username);
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<Users> findById(@PathVariable Long id){//work
        return  userService.findById(id);
    }

    @GetMapping("/listAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Users> listAll(){//work
        return userService.listAll();
    }

    @GetMapping("/me")
    public Users me(){//work
        return getService.getCurrentUser();
    }

    @GetMapping("/allOnline/{online}")
    public List<Users> listAllOnline(@PathVariable boolean online){//work
        return userService.listAllOnline(online);
    }

    @GetMapping("/join/{chatRoomName}")
    public Optional<Users> join(@PathVariable String chatRoomName){
        return userService.joinToChatRoom(chatRoomName);
    }

    @GetMapping("/addRole/{username}/{roleName}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Users addRole(@PathVariable String username,
                         @PathVariable String roleName){
        return userService.addRoleToUser(username,roleName);
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request
            , HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Users user = userService.findByUsername(username).orElseThrow(()-> new EntityNotFoundException());
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+1000 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles()
                                .stream().map(Role::getRole)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else{
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

