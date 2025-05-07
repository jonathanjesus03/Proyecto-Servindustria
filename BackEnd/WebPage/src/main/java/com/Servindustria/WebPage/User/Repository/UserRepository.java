package com.Servindustria.WebPage.User.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.User.Model.UserAccount;


@Repository 
public interface UserRepository extends JpaRepository <UserAccount, Integer> {
    Optional<UserAccount> findByEmail(String email);
}
