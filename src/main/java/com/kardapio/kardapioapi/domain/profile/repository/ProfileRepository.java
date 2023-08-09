package com.kardapio.kardapioapi.domain.profile.repository;

import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<ProfileModel, Long> {
}
