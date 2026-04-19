package org.example.mrdverkin.service;

import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.services.MainInstallerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    @BeforeEach
    public void setup() {
        installerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createInstallerTest(){
        assertEquals(0,userRepository.findAll().size());
        assertEquals(0,installerRepository.findAll().size());

        mainInstallerService.createInstaller("test", "12346427852");

        assertEquals(1,userRepository.findAll().size());
        assertNotNull(userRepository.findAll().get(0).getHashUser());
        assertEquals(1,installerRepository.findAll().size());
        assertNotNull(installerRepository.findAll().get(0).getUser());
    }
}
