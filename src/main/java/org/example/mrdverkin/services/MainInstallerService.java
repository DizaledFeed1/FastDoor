package org.example.mrdverkin.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mrdverkin.controllers.api.exception.BadRequestException;
import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.InstallerDto;
import org.example.mrdverkin.dto.InstallerInfo;
import org.example.mrdverkin.dto.InstallerUpdateDto;
import org.example.mrdverkin.mapper.MainInstallerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MainInstallerService {
    private final InstallerRepository installerRepository;

    public List<Installer> getAllInstallers() {return installerRepository.findAll();}
    public Installer findInstallerById(UUID id) {return installerRepository.findInstallersById(id);}

    public void deleteInstallerById(UUID id) {
        if (!installerRepository.existsById(id)) {
            throw new IllegalArgumentException("Установщик не найден");
        }
        installerRepository.deleteById(id);}

    public void createInstaller(String fullName, String phone) {
            Installer installer = new Installer();
            installer.setFullName(fullName);
            installer.setPhone(phone);
            installerRepository.save(installer);
    }

    public ResponseEntity<Void> updateInstaller(UUID id, String fullName, String phone) {
        Installer installer = findInstallerById(id);
        if (installer == null) {
            return ResponseEntity.notFound().build();
        }
        installer.setFullName(fullName);
        installer.setPhone(phone);
        installerRepository.save(installer);
        return ResponseEntity.ok().build();
    }

    public void updateInstaller(InstallerUpdateDto installerDto) {
        Installer installer = installerRepository.findById(installerDto.getId())
                .orElseThrow(() -> {
                    log.warn("Installer not found. id={}", installerDto.getId());
                    return new EntityNotFoundException();
                });

        installer.setFullName(installerDto.getFullName());
        installer.setPhone(installerDto.getPhone());
        installer.setTgId(installerDto.getTgId());
        installer.setMaxId(installerDto.getMaxId());

        installerRepository.save(installer);
    }

    public ResponseEntity<List<InstallerInfo>> getWorkloadDate(Date date) {
        return ResponseEntity.ok().body(installerRepository.searchDoorbyDate(date, Condition.DELETED));
    }

    @Transactional
    public void selectInstaller(InstallerInfo installerInfo, OrderRepository orderRepository, BotService botService) {
        try {
            Order oldOrder = orderRepository.findByOrderId(installerInfo.getOrderId());
            //todo Нужно сделать поиск по id а не по fullName
            Installer installer = installerRepository.findByName(installerInfo.getInstallerFullName());

            oldOrder.setInstaller(installer);
            oldOrder.setMessageMainInstaller(installerInfo.getInstallerComment());
            oldOrder.setCondition(Condition.ASSIGNED);

            Order newOrder = new Order();
            newOrder.setFullName(oldOrder.getFullName());
            newOrder.setAddress(oldOrder.getAddress());
            newOrder.setDateOrder(oldOrder.getDateOrder());
            newOrder.setPhone(oldOrder.getPhone());
            newOrder.setMessageSeller(oldOrder.getMessageSeller());
            newOrder.setMessageMainInstaller(installerInfo.getInstallerComment());
            newOrder.setDoorLimits(oldOrder.getDoorLimits());
            newOrder.setFrontDoorQuantity(oldOrder.getFrontDoorQuantity());
            newOrder.setInDoorQuantity(oldOrder.getInDoorQuantity());
            newOrder.setInstaller(installer);
            newOrder.setUser(oldOrder.getUser());

            if (oldOrder.getInstaller() == null) {
                botService.selectMessage(newOrder);
            }else if (oldOrder.getInstaller().getFullName().equalsIgnoreCase(installerInfo.getInstallerFullName())){
                botService.modificationMessage(newOrder, oldOrder);
            } else {
                botService.selectMessage(newOrder);
                botService.deleteMessage(oldOrder);
            }
        } catch (Exception e) {
            throw new BadRequestException("Ошибка выбора установщика", e);
        }
    }

    public Optional<InstallerDto> getInstallerByPhone(String phone) {
        return installerRepository.findByPhone(phone)
                .map(MainInstallerMapper::toInstallerDtoWithoutOrders);
    }
}
