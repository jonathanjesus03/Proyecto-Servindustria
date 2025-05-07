package com.Servindustria.WebPage.User.Service;

import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.User.Model.UserAccount;
import com.Servindustria.WebPage.User.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserService {
    
    private final UserRepository usuerRepo;

    public void createUser(UserAccount user){
         usuerRepo.save(user);
    }
}
