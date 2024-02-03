package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private IClientService clientServ;

    @GetMapping("/client/list")
    public List<Client> getClients(){
        return clientServ.clientList();
    }

    @GetMapping("/client/list/{clientId}")
    public Client getClients(@PathVariable("clientId") Long clientId){
        return clientServ.findClient(clientId);
    }

    @PostMapping("/client/create")
    public void createClient(@RequestBody Client client){
        clientServ.saveClient(client);
    }

    @DeleteMapping("/client/delete/{clientId}")
    public void deleteClient(@PathVariable Long clientId){
        clientServ.deleteClient(clientId);
    }

    @PutMapping("/client/edit/{clientId}")
    public void editClient(@PathVariable Long clientId,
                            @RequestBody Client client){
        clientServ.editClient(clientId,client);
    }
}
