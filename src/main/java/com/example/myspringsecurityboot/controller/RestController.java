package com.example.myspringsecurityboot.controller;

import com.example.myspringsecurityboot.model.User;
import com.example.myspringsecurityboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/rest/")
@CrossOrigin
public class RestController {
    @Autowired
    private UserService userService;
    @GetMapping(value = "users")
    public ResponseEntity<List<User>> users(){
        List<User> userList=userService.getListUser();
        return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
    }
}
