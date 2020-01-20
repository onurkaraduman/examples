package com.onrkrdmn.controller;

import com.onrkrdmn.repository.UserRepository;
import com.onrkrdmn.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity(userRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> byId = userRepository.findById(id);
        userRepository.delete(byId.get());
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
