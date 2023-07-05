package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users create(Users user){
        return usersRepository.save(user);
    }
    @Override
    public String changeUsername(Long id, String username){
        Users user = usersRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
        user.setUsername(username);
        usersRepository.save(user);
        return "Username has been changed";
    }
    @Override
    public String deleteUser(Long id){
        usersRepository.deleteById(id);
        return "User has been deleted";
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }


}
