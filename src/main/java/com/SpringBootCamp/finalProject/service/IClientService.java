package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Client;

import java.util.List;

public interface IClientService {

    public void saveClient(Client client);

    public List<Client> clientList();

    public Client findClient(Long clientId);

    public void deleteClient(Long clientId);

    public void editClient(Long clientId,Client updatedClient);
}
