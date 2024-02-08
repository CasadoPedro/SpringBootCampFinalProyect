package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.exception.EmailAlreadyTakenException;
import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.repository.IClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService implements IClientService{

    @Autowired
    private IClientRepository clientRepo;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Client saveClient(Client client) {
        try{
            return clientRepo.save(client);
        }catch (DataIntegrityViolationException ex){
            throw new EmailAlreadyTakenException("Email already taken", ex);
        }
    }


    @Override
    public List<Client> clientList() {
        return clientRepo.findAll();
    }

    @Override
    public Client findClient(Long clientId) {
        return clientRepo.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " not found"));
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepo.deleteById(clientId);
    }

    @Override
    public Client editClient(Long clientId,@Valid Client updatedClient) {
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " not found"));
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updatedClient, client);
        return this.saveClient(client);
    }
}
