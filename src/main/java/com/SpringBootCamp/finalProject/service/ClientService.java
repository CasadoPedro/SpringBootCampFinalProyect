package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService{

    @Autowired
    private IClientRepository clientRepo;

    @Override
    public void saveClient(Client client) {
        clientRepo.save(client);
    }

    @Override
    public List<Client> clientList() {
        return clientRepo.findAll();
    }

    @Override
    public Client findClient(Long clientId) {
        return clientRepo.findById(clientId).orElse(null);
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepo.deleteById(clientId);
    }

    @Override
    public void editClient(Long clientId, Client updatedClient) {
        Client client = this.findClient(clientId);
        client.setName(updatedClient.getName());
        client.setLast_name(updatedClient.getLast_name());
        client.setEmail(updatedClient.getEmail());
        this.saveClient(client);
    }
}
