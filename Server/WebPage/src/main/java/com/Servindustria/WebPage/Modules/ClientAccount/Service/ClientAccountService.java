package com.Servindustria.WebPage.Modules.ClientAccount.Service;

import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Modules.ClientAccount.Model.ClientAccount;
import com.Servindustria.WebPage.Modules.ClientAccount.Repository.ClientAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ClientAccountService {
    
    private final ClientAccountRepository usuerRepo;

    public void createUser(ClientAccount user){
         usuerRepo.save(user);
    }
}
