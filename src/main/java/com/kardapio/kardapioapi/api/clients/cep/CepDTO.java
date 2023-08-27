package com.kardapio.kardapioapi.api.clients.cep;

public record CepDTO(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        String ibge,
        String gia,
        String ddd,
        String siafi
        ) {}