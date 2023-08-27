package com.kardapio.kardapioapi.domain.address.service;

import com.kardapio.kardapioapi.domain.address.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService (AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
}
