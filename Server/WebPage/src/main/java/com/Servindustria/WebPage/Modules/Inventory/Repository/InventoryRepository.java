package com.Servindustria.WebPage.Modules.Inventory.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Modules.Inventory.Model.Inventory;


@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
