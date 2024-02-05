package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.model.Sale;
import com.SpringBootCamp.finalProject.repository.IClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService{

    @Autowired
    private IClientRepository clientRepo;
    ModelMapper modelMapper = new ModelMapper();

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
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " not found"));
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updatedClient, client);
        this.saveClient(client);
    }
}
