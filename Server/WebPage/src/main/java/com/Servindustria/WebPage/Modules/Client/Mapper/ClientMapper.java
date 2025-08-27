package com.Servindustria.WebPage.Modules.Client.Mapper;

import com.Servindustria.WebPage.Modules.Client.DTO.CompanyClientDto;
import com.Servindustria.WebPage.Modules.Client.DTO.NaturalClientDto;

import java.util.stream.Collectors;

import com.Servindustria.WebPage.Modules.Client.DTO.ClientDto;
import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Client.Model.CompanyClient;
import com.Servindustria.WebPage.Modules.Client.Model.NaturalClient;
import com.Servindustria.WebPage.Modules.Quote.Mapper.QuoteMapper;

public class ClientMapper {

    public static ClientDto toDto(Client client) {
        if (client == null) {
            return null;
        }

        if (client instanceof CompanyClient) {
            CompanyClient companyClient = (CompanyClient) client;
            CompanyClientDto dto = new CompanyClientDto();
            setCommonFields(dto, companyClient);
            dto.setNameReasonSoc(companyClient.getNameReasonSoc());
            dto.setAddress(companyClient.getAddress());
            dto.setReference(companyClient.getReference());
            dto.setTypeClient("EMPRESA");
            if (client.getQuotes() != null) {
                dto.setQuotes(client.getQuotes().stream()
                    .map(QuoteMapper::toDTO)
                    .collect(Collectors.toList()));
            }
            return dto;
        } else if (client instanceof NaturalClient) {
            NaturalClient naturalClient = (NaturalClient) client;
            NaturalClientDto dto = new NaturalClientDto();
            setCommonFields(dto, naturalClient);
            dto.setName(naturalClient.getName());
            dto.setLastName(naturalClient.getLastName());
            dto.setBirthDay(naturalClient.getBirthDay());
            dto.setGender(naturalClient.getGender().toString());
            dto.setTypeClient("PERSONA");
            if (client.getQuotes() != null) {
                dto.setQuotes(client.getQuotes().stream()
                    .map(QuoteMapper::toDTO)
                    .collect(Collectors.toList()));
            }
            return dto;
        }
        throw new IllegalArgumentException("Tipo de cliente desconocido: " + client.getClass().getSimpleName());
    }

   
    public static Client toEntity(ClientDto dto) {
        if (dto == null) {
            return null;
        }

        if (dto instanceof CompanyClientDto) {
            CompanyClientDto companyDto = (CompanyClientDto) dto;
            CompanyClient entity = new CompanyClient();
            setCommonFields(entity, companyDto);
            entity.setNameReasonSoc(companyDto.getNameReasonSoc());
            entity.setAddress(companyDto.getAddress());
            entity.setReference(companyDto.getReference());
            return entity;
        } else if (dto instanceof NaturalClientDto) {
            NaturalClientDto naturalDto = (NaturalClientDto) dto;
            NaturalClient entity = new NaturalClient();
            setCommonFields(entity, naturalDto);
            entity.setName(naturalDto.getName());
            entity.setLastName(naturalDto.getLastName());
            entity.setBirthDay(naturalDto.getBirthDay());
            entity.setGender(naturalDto.getGender());
            return entity;
        }
        throw new IllegalArgumentException("Tipo de DTO desconocido: " + dto.getClass().getSimpleName());
    }

    private static void setCommonFields(ClientDto dto, Client client) {
        dto.setId(client.getId());
        dto.setCod(client.getCode());
        dto.setTypeDoc(client.getDocumentType());
        dto.setDocumentNumber(client.getDocumentNumber());
        dto.setPhone1(client.getPhone1());
        dto.setPhone2(client.getPhone2());
        dto.setEmail(client.getEmail());
        if (client.getAccount() != null) {
            dto.setIdAccount(client.getAccount().getId());
        }
    }

    private static void setCommonFields(Client client, ClientDto dto) {
        client.setId(dto.getId());
        client.setCode(dto.getCod());
        client.setDocumentType(dto.getTypeDoc());
        client.setDocumentNumber(dto.getDocumentNumber());
        client.setPhone1(dto.getPhone1());
        client.setPhone2(dto.getPhone2());
        client.setEmail(dto.getEmail());
    }
}