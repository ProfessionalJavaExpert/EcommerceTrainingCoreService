package com.example.coreService.services;

import com.example.coreService.entities.Commande;
import com.example.coreService.feignClient.ProductServiceFeignClient;
import com.example.coreService.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    private static final String TOPIC = "notification_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;



    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }



    public Commande createCommande(Commande commande) {
      // i want to add this comment to git repo
         var savedCommande = commandeRepository.save(commande);
        // send message through kafka to notification service
        kafkaTemplate.send(TOPIC, String.valueOf(savedCommande.getId()));



        return savedCommande;
    }





    public List<Commande> saveAll(List<Commande> commandeList){
        return commandeRepository.saveAll(commandeList);
    }

    public Commande updateCommande(Long id, Commande updatedCommande) {
        if (commandeRepository.existsById(id)) {
            updatedCommande.setId(id);
            return commandeRepository.save(updatedCommande);
        }
        return null;
    }

    public boolean deleteCommande(Long id) {
        if (commandeRepository.existsById(id)) {
            commandeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ResponseEntity<Void> deleteProduct(String id) {
        return productServiceFeignClient.deleteProduct(id);
    }

}
