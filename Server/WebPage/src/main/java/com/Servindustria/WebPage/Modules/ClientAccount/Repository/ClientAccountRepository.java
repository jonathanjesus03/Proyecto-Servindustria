package com.Servindustria.WebPage.Modules.ClientAccount.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.ClientAccount.Model.ClientAccount;


@Repository 
public interface ClientAccountRepository extends JpaRepository <ClientAccount, Long> {
    @Query ("SELECT ca FROM ClientAccount ca WHERE ca.email= :email")
    Optional<ClientAccount> findByEmail(@Param("email") String email);

}
