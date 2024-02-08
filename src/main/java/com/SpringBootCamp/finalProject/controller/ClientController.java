package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.service.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClientService clientServ;

    @GetMapping("/list")
    public ResponseEntity<List<Client>> getAllClients(){
        return ResponseEntity.ok(clientServ.clientList());
    }

    @GetMapping("/list/{clientId}")
    public ResponseEntity<Client> getClient(@PathVariable("clientId") Long clientId){
        return ResponseEntity.ok(clientServ.findClient(clientId));
    }

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody @Valid Client client){
        return new ResponseEntity<>(clientServ.saveClient(client), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity deleteClient(@PathVariable Long clientId){
        clientServ.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{clientId}")
    public ResponseEntity<Client> editClient(@PathVariable Long clientId,
                                             @RequestBody Client client){
        return new ResponseEntity<>(clientServ.editClient(clientId,client), HttpStatus.OK);
    }
}
