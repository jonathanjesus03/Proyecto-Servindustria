package com.Servindustria.WebPage.Modules.Client.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Client.Model.CompanyClient;
import com.Servindustria.WebPage.Modules.Client.Model.NaturalClient;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    @Query ("SELECT ca FROM Client ca WHERE ca.email= :email")
    Optional<Client> findByEmail(@Param("email") String email);

    @Query("SELECT nc FROM NaturalClient nc WHERE nc.id = :id")
    Optional<NaturalClient> findNaturalClientById(@Param("id") Long id);

    @Query("SELECT cc FROM CompanyClient cc WHERE cc.id = :id")
    Optional<CompanyClient> findCompanyClientById(@Param("id") Long id);}
