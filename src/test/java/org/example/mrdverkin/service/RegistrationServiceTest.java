package org.example.mrdverkin.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mrdverkin.dataBase.Entitys.Role;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.RegistrationForm;
import org.example.mrdverkin.dto.auth.InviteRegistrationRequestDto;
import org.example.mrdverkin.dto.auth.InviteRegistrationResponseDto;
import org.example.mrdverkin.services.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@Sql(scripts = "/data/registration_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RegistrationServiceTest {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void inviteRegistrationTest() {
        InviteRegistrationResponseDto responseDto = registrationService.inviteRegistration("a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2");

        assertNotNull(responseDto);
        assertEquals("Алексей Смирнов", responseDto.getNickname());
        assertEquals(Role.ROLE_INSTALLER, responseDto.getRole());
    }

    @Test
    public void inviteRegistrationNotFoundTest() {
        assertThrows(EntityNotFoundException.class, () -> registrationService.inviteRegistration("a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f3"));
    }

    @Test
    public void testRegistration() {
        User oldUser = userRepository.findByInviteCode("a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2")
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        assertNotNull(oldUser.getNickname());
        assertNotNull(oldUser.getInviteCode());
        assertNull(oldUser.getPassword());
        assertNull(oldUser.getUsername());

        RegistrationForm registrationForm = RegistrationForm.builder()
                .username("test")
                .password("testPassword")
                .confirm("testPassword")
                .inviteCode("a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2")
                .build();

        registrationService.registration(registrationForm);

        User newUser = userRepository.findById(oldUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        assertNotNull(newUser.getNickname());
        assertNull(newUser.getInviteCode());
        assertNotNull(newUser.getPassword());
        assertNotNull(newUser.getUsername());
    }

    @Test
    public void registrationNotFoundTest() {
        assertThrows(EntityNotFoundException.class, () -> registrationService.registration(RegistrationForm.builder()
                .username("test")
                .password("testPassword")
                .confirm("testPassword")
                .inviteCode("a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f3")
                .build()));

    }
}
