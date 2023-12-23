package com.kardapio.kardapioapi.domain.profile;

import com.kardapio.kardapioapi.domain.address.dto.AddressDTO;
import com.kardapio.kardapioapi.domain.address.dto.AddressMapper;
import com.kardapio.kardapioapi.domain.profile.dto.ProfileDTO;
import com.kardapio.kardapioapi.domain.profile.dto.ProfileMapper;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.utils.FakeObjectsFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ProfileMapperTest {

    @InjectMocks
    private ProfileMapper profileMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressMapper addressMapper;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should convert a DTO in model with address")
    void testGetProfileId() {

        UserModel mockUser = FakeObjectsFactory.getDefaultUserModel(true);
        ProfileDTO mockProfileDTO = FakeObjectsFactory.getDefaultProfileDto(true);

        Mockito.when(this.userRepository.findById(mockUser.getId()))
                .thenReturn(Optional.of(mockUser));

        for (AddressDTO address : mockProfileDTO.address()) {
            Mockito.when(this.addressMapper.toModel(address))
                    .thenReturn(address);
        }

        Mockito.when(this.addressMapper.toModel(mockProfileDTO.address().))
                .thenReturn(Optional.of(mockUser));

        ProfileModel profile = this.profileMapper.toModel(mockProfileDTO, mockUser.getId());

        ProfileModel profileModelExpected = mockUser.getProfileModel();

        Assertions.assertNotNull(profile);
        Assertions.assertEquals(profileModelExpected, profile);
    }
}
