package com.Servindustria.WebPage.Modules.Client.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Client.DTO.ClientDto;
import com.Servindustria.WebPage.Modules.Client.Mapper.ClientMapper;
import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class ClientService {
    private final ClientRepository clientRepository;

     // Create
    public ClientDto createClient(ClientDto dto) {
        Client client = ClientMapper.toEntity(dto);
        Client saved = clientRepository.save(client);
        return ClientMapper.toDto(saved);
    }

    // Get by ID
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));
        return ClientMapper.toDto(client);
    }

    // Get All
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
            .map(ClientMapper::toDto)
            .collect(Collectors.toList());
    }

    // Update
    public ClientDto updateClient(Long id, ClientDto dto) {
        Client existingClient = clientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));

        Client updatedClient = ClientMapper.toEntity(dto);
        updatedClient.setId(id);
        updatedClient.setAccount(existingClient.getAccount());
        Client saved = clientRepository.save(updatedClient);
        return ClientMapper.toDto(saved);
    }

    // Delete
    public void deleteClient(Long id) {
        clientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));
        clientRepository.deleteById(id);
    }
}
