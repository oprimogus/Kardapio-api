package com.kardapio.kardapioapi.domain.profile.repository;

import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileModel, Long> {
}
