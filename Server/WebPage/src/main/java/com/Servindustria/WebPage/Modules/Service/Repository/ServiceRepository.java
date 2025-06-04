package com.Servindustria.WebPage.Modules.Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.Service.Model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>{
    
}
