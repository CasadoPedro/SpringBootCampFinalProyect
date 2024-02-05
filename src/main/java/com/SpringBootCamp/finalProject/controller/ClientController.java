package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClientService clientServ;

    @GetMapping("/list")
    public List<Client> getClients(){
        return clientServ.clientList();
    }

    @GetMapping("/list/{clientId}")
    public Client getClients(@PathVariable("clientId") Long clientId){
        return clientServ.findClient(clientId);
    }

    @PostMapping("/create")
    public void createClient(@RequestBody Client client){
        clientServ.saveClient(client);
    }

    @DeleteMapping("/delete/{clientId}")
    public void deleteClient(@PathVariable Long clientId){
        clientServ.deleteClient(clientId);
    }

    @PutMapping("/edit/{clientId}")
    public void editClient(@PathVariable Long clientId,
                            @RequestBody Client client){
        clientServ.editClient(clientId,client);
    }
}
