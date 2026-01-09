package org.example.mrdverkin.service;

import org.example.mrdverkin.MrDverkinApplication;
import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.InstallerInfo;
import org.example.mrdverkin.services.BotService;
import org.example.mrdverkin.services.InstallerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@SpringBootTest(classes = MrDverkinApplication.class)
@ActiveProfiles("dev")
class InstallerServiceTest {

    @Autowired
    private InstallerService service;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DoorLimitsRepository doorLimitsRepository;
    @Autowired
    private InstallerRepository installerRepository;
    @Autowired
    private BotService botService;

    private DoorLimits doorLimits;
    private Order order;
    private Installer installer;

    @BeforeEach
    void init() {
        DoorLimits doorLimitsToSave = new DoorLimits();
        java.util.Date utilDate = new java.util.Date();
        doorLimitsToSave.setLimitDate(new java.sql.Date(utilDate.getTime()));
        doorLimitsToSave.setFrontDoorQuantity(2);
        doorLimitsToSave.setInDoorQuantity(10);

        doorLimits = doorLimitsRepository.save(doorLimitsToSave);

        Order orderToSave = new Order();
        orderToSave.setFullName("Test");
        orderToSave.setAddress("Test");
        orderToSave.setDateOrder(LocalDate.MAX);
        orderToSave.setPhone("+73123112");
        orderToSave.setMessageSeller("Test");
        orderToSave.setDoorLimits(doorLimits);
        orderToSave.setFrontDoorQuantity(2);
        orderToSave.setInDoorQuantity(2);
        order = orderRepository.save(orderToSave);

        Installer installerToSave = new Installer();
        installerToSave.setFullName("Test");
        installerToSave.setPhone("+79137488501");
        installerToSave.setTgId("623201152");
        installer = installerRepository.save(installerToSave);
    }

    @Test
    void selectInstallerTest(){
        InstallerInfo installerInfo = new InstallerInfo(order.getId(),installer.getFullName(),1L, 3L);
        installerInfo.setInstallerComment("Test");

        service.selectInstaller(installerInfo, orderRepository, botService);
    }
}
