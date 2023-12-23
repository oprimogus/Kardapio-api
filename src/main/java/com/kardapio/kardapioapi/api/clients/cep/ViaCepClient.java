package com.kardapio.kardapioapi.api.clients.cep;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kardapio.kardapioapi.exceptions.apis.ViaCEPException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ViaCepClient {

    @Value("${apis.viacep.url}")
    private String baseUrl;

    public ViaCepClient() {}

    public CepDTO findCep(String cep) {
        try {
            var client = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + cep + "/json/"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(response.body(), CepDTO.class);

        } catch (URISyntaxException | IOException e) {
            throw new ViaCEPException("Failed to process the CEP request.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ViaCEPException("The CEP request was interrupted.", e);
        }
    }
}
