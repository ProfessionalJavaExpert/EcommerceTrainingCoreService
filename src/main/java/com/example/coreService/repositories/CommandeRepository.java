package com.example.coreService.repositories;


import com.example.coreService.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {


    List<Commande> findCommandesByClientId(Long clientId);

    //Find commandes with a specific product count
    @Query("SELECT c FROM Commande c WHERE SIZE(c.products) = :productCount")
    List<Commande> findCommandesByProductCount(@Param("productCount") int productCount);

    //Find commandes ordered by ID descending:
    @Query("SELECT c FROM Commande c ORDER BY c.id DESC")
    List<Commande> findAllCommandesOrderedByIdDesc();


    //native
    @Query(value = "SELECT c.* FROM Commande c  WHERE c.client_id = :clientId ", nativeQuery = true)
    List<Commande> findCommandesByClientIdAndProductIdNative(@Param("clientId") Long clientId);

}