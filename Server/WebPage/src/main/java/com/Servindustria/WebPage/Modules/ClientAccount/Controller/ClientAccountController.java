package com.Servindustria.WebPage.Modules.ClientAccount.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.Modules.ClientAccount.Model.ClientAccount;
import com.Servindustria.WebPage.Modules.ClientAccount.Service.ClientAccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ClientAccountController {
    private final ClientAccountService userService;

    @PostMapping("/createUser")
    public void createUsuario(@RequestBody ClientAccount user){
        userService.createUser(user);
    }
    
    @PostMapping("/demo")
    public String welcome() {
        return "Welcome from secure endpoint";
    }
    
}
