package org.example.mrdverkin;

import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Role;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.LoginRequest;
import org.example.mrdverkin.dto.RegistrationForm;
import org.example.mrdverkin.dto.auth.InviteRegistrationRequestDto;
import org.example.mrdverkin.dto.auth.InviteRegistrationResponseDto;
import org.example.mrdverkin.services.MainInstallerService;
import org.example.mrdverkin.services.RegistrationService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InstallerFullProcessTest {

    @Autowired
    private MainInstallerService mainInstallerService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InstallerRepository installerRepository;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String inviteCode;
    private static String login;

    @Test
    @Order(1)
    public void createInstaller() {
        assertEquals(0, userRepository.findAll().size());
        assertEquals(0, installerRepository.findAll().size());

        mainInstallerService.createInstaller("testFullName", "81234567890");

        assertEquals(1, userRepository.findAll().size());
        assertEquals(1, installerRepository.findAll().size());

        inviteCode = userRepository.findAll().get(0).getInviteCode();
    }

    @Test
    @Order(2)
    public void inviteRegistration() {
        InviteRegistrationResponseDto responseDto = registrationService.inviteRegistration(InviteRegistrationRequestDto.builder()
                        .inviteCode(inviteCode)
                .build());

        assertEquals("testFullName", responseDto.getNickname());
        assertEquals(Role.ROLE_INSTALLER, responseDto.getRole());
    }

    @Test
    @Order(3)
    public void registration() {
        registrationService.registration(RegistrationForm.builder()
                        .username("testUsername")
                        .password("testPassword")
                        .confirm("testPassword")
                        .inviteCode(inviteCode)
                .build());

        User user = userRepository.findAll().get(0);
        Installer installer = installerRepository.findAll().get(0);

        assertEquals("testUsername", user.getUsername());
        assertEquals(inviteCode, user.getInviteCode());
        assertEquals(installer.getUser().getId(), user.getId());

        login = user.getUsername();
    }

    @Test
    @Order(4)
    public void login() {
        LoginRequest request = new LoginRequest();
        request.setUsername(login);
        request.setPassword("testPassword");
        request.setRememberMe(true);

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/api/login",
                request,
                String.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"Login Successful\",\"roles\":\"installer\"}", response.getBody());
    }
}