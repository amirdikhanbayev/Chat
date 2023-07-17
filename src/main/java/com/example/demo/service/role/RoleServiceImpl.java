package com.example.demo.service.role;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleText(String role){
        return roleRepository.findAllByRole(role);
    }
    @Override
    public Role create(Role role){
       return roleRepository.save(role);
    }
    @Override
    public Role changeText(Long id, String text){
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException());
        role.setRole(text);
        return roleRepository.save(role);
    }
    @Override
    public String delete(Long id){
        roleRepository.deleteById(id);
        return "Deleted";
    }
}
