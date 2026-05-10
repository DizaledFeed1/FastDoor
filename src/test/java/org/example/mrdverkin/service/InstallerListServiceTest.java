package org.example.mrdverkin.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mrdverkin.MrDverkinApplication;
import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.installer.InstallerOrderResponseDto;
import org.example.mrdverkin.services.InstallerListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("dev")
@Sql(scripts = "/data/full_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(classes = MrDverkinApplication.class)
public class InstallerListServiceTest {
    @Autowired
    private InstallerListService installerListService;
    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = userRepository.findById(UUID.fromString("55555555-5555-4555-8555-555555555555"))
                .orElseThrow(() -> new EntityNotFoundException("Установщик не найден"));
    }

    @Test
    public void getAssignedAndWorkedSortedOrdersTest() {
        List<InstallerOrderResponseDto> result = installerListService.getSortedOrders(List.of(Condition.ASSIGNED, Condition.WORKED),testUser, Pageable.unpaged()).getContent();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getAllSortedOrdersTest() {
        List<InstallerOrderResponseDto> result = installerListService.getSortedOrders(List.of(), testUser, Pageable.unpaged()).getContent();
        assertNotNull(result);
        assertEquals(4, result.size());
    }
}
