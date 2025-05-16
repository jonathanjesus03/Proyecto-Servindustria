package com.Servindustria.WebPage.User.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.User.Model.UserAccount;
import com.Servindustria.WebPage.User.Service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/createUser")
    public void createUsuario(@RequestBody UserAccount user){
        userService.createUser(user);
    }
    
    @PostMapping("/demo")
    public String welcome() {
        return "Welcome from secure endpoint";
    }
    
}
