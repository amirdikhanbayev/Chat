package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/find/{role}")
    public Optional<Role> findRole(@PathVariable String role){
        return roleService.findRoleText(role);
    }
    @GetMapping("/create")
    public Role create(@RequestBody Role role ){
        return roleService.create(role);
    }
    @PutMapping("/change/{id}/{text}")
    public Role change(@PathVariable Long id, @PathVariable String text){
        return roleService.changeText(id, text);
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        return roleService.delete(id);
    }
}
