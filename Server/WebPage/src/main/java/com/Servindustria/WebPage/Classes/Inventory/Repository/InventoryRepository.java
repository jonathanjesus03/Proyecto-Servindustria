package com.Servindustria.WebPage.Classes.Inventory.Repository;
import com.Servindustria.WebPage.Classes.Inventory.Model.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
