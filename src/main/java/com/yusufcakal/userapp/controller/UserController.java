package com.yusufcakal.userapp.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yusufcakal.userapp.model.User;
import com.yusufcakal.userapp.repository.UserRepository;

@CrossOrigin(maxAge = 3600, origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll(sortByIdDesc());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        User user = userRepository.findOne(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userDetails) {
        User user = userRepository.findOne(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setAge(userDetails.getAge());
        user.setName(userDetails.getName());
        user.setBook(userDetails.getBook());
        user.setUpdateTs(new Timestamp(System.currentTimeMillis()));
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteNote(@PathVariable(value = "id") Long id) {
        User user = userRepository.findOne(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
    
    @SuppressWarnings("deprecation")
	private Sort sortByIdDesc() {
        return new Sort(Sort.Direction.DESC, "updateTs");
    }
}
