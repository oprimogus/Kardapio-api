package com.kardapio.kardapioapi.domain.profile;

import com.kardapio.kardapioapi.domain.address.dto.AddressDTO;
import com.kardapio.kardapioapi.domain.profile.dto.ProfileDTO;
import com.kardapio.kardapioapi.domain.profile.dto.ProfileMapper;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.profile.repository.ProfileRepository;
import com.kardapio.kardapioapi.domain.profile.service.ProfileService;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.domain.user.services.UserService;
import com.kardapio.kardapioapi.exceptions.database.RecordConflictException;
import com.kardapio.kardapioapi.exceptions.database.RecordNotFoundException;
import com.kardapio.kardapioapi.utils.FakeObjectsFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileMapper profileMapper;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return profile ID with success")
    void testGetProfileId() {
        UserModel userExpected = FakeObjectsFactory.getDefaultUserModel(true);
        Mockito.when(this.userService.getUserByAuth()).thenReturn(userExpected);

        Long profileId = this.profileService.getProfileId();

        Assertions.assertNotNull(profileId);
        Assertions.assertEquals(userExpected.getProfileModel().getId(), profileId);
    }

    @Test
    @DisplayName("Should return RecordNotFoundException")
    void testGetProfileIdFail() {
        UserModel userExpected = FakeObjectsFactory.getDefaultUserModel(false);
        Mockito.when(this.userService.getUserByAuth()).thenReturn(userExpected);

        Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            this.profileService.getProfileId();
        });
    }

    @Test
    @DisplayName("Should return Optional profile ID with success")
    void testGetOptionalProfileId() {
        UserModel userExpected = FakeObjectsFactory.getDefaultUserModel(true);

        Optional<Long> profileId = this.profileService.getOptionalProfileId(userExpected);

        Assertions.assertNotNull(profileId);
        Assertions.assertTrue(profileId.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional")
    void testGetOptionalProfileIdEmpty() {
        UserModel userExpected = FakeObjectsFactory.getDefaultUserModel(false);

        Optional<Long> profileId = this.profileService.getOptionalProfileId(userExpected);

        Assertions.assertNotNull(profileId);
        Assertions.assertTrue(profileId.isEmpty());
    }

    @Test
    @DisplayName("Should return a profile")
    void testFindProfile() {
        UserModel userExpected = FakeObjectsFactory.getDefaultUserModel(true);
        ProfileModel profileExpected = userExpected.getProfileModel();
        ProfileDTO profileDTOExpected = new ProfileDTO(
                profileExpected.getName(),
                profileExpected.getLastName(),
                profileExpected.getCpf(),
                profileExpected.getPhone(),
                profileExpected.getPicture(),
                new HashSet<AddressDTO>());
        Mockito.when(this.userService.getUserByAuth()).thenReturn(userExpected);
        Mockito.when(this.profileRepository.findById(anyLong())).thenReturn(Optional.of(userExpected.getProfileModel()));
        Mockito.when(this.profileMapper.toDTO(any(ProfileModel.class)))
                .thenReturn(profileDTOExpected);

        ProfileDTO profile = this.profileService.findProfile();

        Assertions.assertNotNull(profile);
    }
    @Test
    @DisplayName("Should return a RecordNotFoundException when find a profile")
    void testFindProfileFail() {
        UserModel userExpected = FakeObjectsFactory.getDefaultUserModel(false);

        Mockito.when(this.userService.getUserByAuth()).thenReturn(userExpected);

        Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            this.profileService.findProfile();
        });

        Assertions.assertEquals("Não existe perfil para o usuário autenticado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should create a profile without a existing profile")
    void testCreateProfileWithoutExistingProfile() {
        UserModel userMock = FakeObjectsFactory.getDefaultUserModel(false);
        UserModel userMockWithProfileModel = FakeObjectsFactory.getDefaultUserModel(true);
        ProfileModel profileModelMock = userMockWithProfileModel.getProfileModel();
        ProfileDTO inputProfileDTO = new ProfileDTO(
                "John",
                "Doe",
                "24223457861",
                "13982289714",
                null,
                null
        );

        Mockito.when(userService.getUserByAuth()).thenReturn(userMock);
        Mockito.when(profileMapper.toModel(inputProfileDTO, userMock.getId())).thenReturn(profileModelMock);
        Mockito.when(userRepository.save(userMock)).thenReturn(userMock);
        Mockito.when(profileMapper.toDTO(profileModelMock)).thenReturn(inputProfileDTO);

        ProfileDTO result = profileService.createProfile(inputProfileDTO);

        Assertions.assertEquals(inputProfileDTO, result);
        Mockito.verify(userService, times(1)).getUserByAuth();
        Mockito.verify(userRepository, times(1)).save(userMock);
    }

    @Test
    @DisplayName("Should return RecordConflictException because exist a profile for this user")
    void testCreateProfileWithExistingProfile() {
        UserModel userMock = FakeObjectsFactory.getDefaultUserModel(true);
        ProfileDTO inputProfileDTO = new ProfileDTO(
                "John",
                "Doe",
                "24223457861",
                "13982289714",
                null,
                null
        );
        Mockito.when(userService.getUserByAuth()).thenReturn(userMock);
        Assertions.assertThrows(RecordConflictException.class, () -> profileService.createProfile(inputProfileDTO));
    }
}
