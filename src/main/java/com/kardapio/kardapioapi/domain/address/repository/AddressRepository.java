package com.kardapio.kardapioapi.domain.address.repository;

import com.kardapio.kardapioapi.domain.address.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressModel, Long> {

}
