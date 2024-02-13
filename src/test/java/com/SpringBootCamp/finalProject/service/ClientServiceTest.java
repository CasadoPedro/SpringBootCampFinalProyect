package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.exception.EmailAlreadyTakenException;
import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.repository.IClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private IClientRepository clientRepo;
    private ClientService underTest;

    @BeforeEach
    void setUp(){
        underTest = new ClientService(clientRepo);
    }

    @Test
    void client_list_method_calls_client_repository_find_all() {
        //when
        underTest.clientList();
        //then
        verify(clientRepo).findAll();
    }

    @Test
    void client_without_email_taken_saves_correctly() {
        //given
        Client client = new Client(1L,"John","Doe","johndoe@gmail.com",new ArrayList<>());
        //when
        underTest.saveClient(client);
        //then
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepo).save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();
        assertThat(capturedClient).isEqualTo(client);
    }
    @Test
    void client_with_email_taken_throws_EmailAlreadyTakenException() {
        //given
        Client client = new Client(1L,"John","Doe","johndoe@gmail.com",new ArrayList<>());
        given(clientRepo.save(client)).willThrow(DataIntegrityViolationException.class);
        //when
        //then
        assertThatThrownBy(() -> underTest.saveClient(client))
                .isInstanceOf(EmailAlreadyTakenException.class)
                .hasMessageContaining("Email already taken");
        verify(clientRepo).save(client);
    }

    @Test
    void find_client_with_invalid_id_throws_EntityNotFoundException() {
        //given
        Long clientId = 999L;
        //when
        //then
        assertThatThrownBy(()-> underTest.findClient(clientId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client with id " + clientId + " not found");
        verify(clientRepo).findById(clientId);
    }

    @Test
    void delete_client_calls_repository_delete_by_id_with_valid_client_id() {
        //given
        Long clientId = 999L;
        //when
        underTest.deleteClient(clientId);
        //then
        ArgumentCaptor<Long> clientIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(clientRepo).deleteById(clientIdArgumentCaptor.capture());
        Long capturedClientId = clientIdArgumentCaptor.getValue();
        assertThat(capturedClientId).isEqualTo(clientId);
        verify(clientRepo).deleteById(clientId);
    }

    @Test
    void edit_client_with_invalid_id_throws_EntityNotFoundException() {
        //given
        Long clientId = 999L;
        //when
        //then
        assertThatThrownBy(()-> underTest.editClient(clientId,any()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client with id " + clientId + " not found");
        verify(clientRepo,never()).save(any());
    }
    @Test
    void edit_client_correctly_updates_old_client() {
        //given
        Client client = new Client(1L,"John","Doe","johndoe@gmail.com",new ArrayList<>());
        Client updatedClient = new Client(1L,"Roger",null,"rogerdoe@gmail.com",new ArrayList<>());
        given(clientRepo.findById(any())).willReturn(Optional.of(client));
        //when
        underTest.editClient(any(),updatedClient);
        //then
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepo).save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();
        assertThat(capturedClient)
                .usingRecursiveComparison()
                .isEqualTo(new Client(1L,"Roger","Doe","rogerdoe@gmail.com",new ArrayList<>()));
        verify(clientRepo).save(capturedClient);
    }
}