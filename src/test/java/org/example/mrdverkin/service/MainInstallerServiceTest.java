package org.example.mrdverkin.service;

import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.mainInstaller.InstallerResponseDto;
import org.example.mrdverkin.services.MainInstallerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
public class MainInstallerServiceTest {
    @Autowired
    private MainInstallerService mainInstallerService;
    @Autowired
    private InstallerRepository installerRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void setup() {
        installerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createInstallerTest(){
        installerRepository.deleteAll();
        userRepository.deleteAll();

        assertEquals(0,userRepository.findAll().size());
        assertEquals(0,installerRepository.findAll().size());

        mainInstallerService.createInstaller("test", "12346427852");

        assertEquals(1,userRepository.findAll().size());
        assertNotNull(userRepository.findAll().get(0).getInviteCode());
        assertEquals(1,installerRepository.findAll().size());
        assertNotNull(installerRepository.findAll().get(0).getUser());
    }

    @Test
    @Sql(scripts = "/data/registration_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllInstallersTest(){
        List<InstallerResponseDto> response = mainInstallerService.getAllInstallers();

        assertNotNull(response);
        assertEquals(2,response.size());
    }
}
