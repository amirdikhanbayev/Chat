package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.get.GetService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GetService getService;

    @PostMapping("/create")
    public Users create(Users users){
        return userService.create(users);
    }

    @PatchMapping("/changeUsername")
    public Users changeUsername(Long id, String username){
        return userService.changeUsername(id, username);
    }

    @DeleteMapping  ("/delete")
    public String delete(Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/findByUsername")
    public Optional<Users> findByUsername(String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/findById")
    public Optional<Users> findById(Long id){
        return  userService.findById(id);
    }

    @GetMapping("/ListAll")
    public List<Users> listAll(){
        return userService.listAll();
    }

    @GetMapping("/me")
    public Users me(){
        return getService.getCurrentUser();
    }

    @GetMapping("/allOnline")
    public List<Users> listAllOnline(boolean online){
        return userService.listAllOnline(online);
    }
}
