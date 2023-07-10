package com.example.demo.service.user;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users create(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }
    @Override
    public Users changeUsername(Long id, String username){
        Users user = usersRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
        user.setUsername(username);
        return usersRepository.save(user);
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

    @Override
    public Optional<Users> findById(Long id){
        return  usersRepository.findById(id);
    }
    @Override
    public List<Users> listAll(){
        return usersRepository.findAll();
    }

    @Override
    public void online(Long id) {
        usersRepository.online(id);
    }

    @Override
    public void offline(Long id) {
        usersRepository.offline(id);
    }

    @Override
    public List<Users> listAllOnline(boolean online){
        return usersRepository.findByOnline(online);
    }

}