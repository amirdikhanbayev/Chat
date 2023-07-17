package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.get.GetService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        getService.getCurrentUser().setOnline(false);
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

    @DeleteMapping  ("/delete/{id}" )
    public String delete(@PathVariable Long id){//work
        return userService.deleteUser(id);
    }

    @GetMapping("/findByUsername/{username}")
    public Optional<Users> findByUsername(@PathVariable String username){//work
        return userService.findByUsername(username);
    }

    @GetMapping("/findById/{id}")
    public Optional<Users> findById(@PathVariable Long id){//work
        return  userService.findById(id);
    }

    @GetMapping("/ListAll")
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
    public Users join(@PathVariable String chatRoomName){
        return userService.joinToChatRoom(chatRoomName);
    }
}
