package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.InstallerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InstallerService {
    @Autowired
    private InstallerRepository installerRepository;

    public List<Installer> getAllInstallers() {return installerRepository.findAll();}

    public Installer findInstallerById(Long id) {return installerRepository.findInstallersById(id);}

    public void deleteInstallerById(Long id) {
        if (!installerRepository.existsById(id)) {
            throw new IllegalArgumentException("Установщик не найден");
        }
        installerRepository.deleteById(id);}

    public void createInstaller(String fullName, String phone) {
        try {
            Installer installer = new Installer();
            installer.setFullName(fullName);
            installer.setPhone(phone);
            installerRepository.save(installer);
        } catch (Exception e) {}
    }

    public ResponseEntity<Void> updateInstaller(Long id, String fullName, String phone) {
        Installer installer = findInstallerById(id);
        if (installer == null) {
            return ResponseEntity.notFound().build();
        }
        installer.setFullName(fullName);
        installer.setPhone(phone);
        installerRepository.save(installer);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<InstallerInfo>> getWorkloadDate(Date date) {
//        List<InstallerInfo> installerInfos = installerRepository.searchDoorbyDate(date);
        return  ResponseEntity.ok().body(installerRepository.searchDoorbyDate(date, Condition.DELETED));
    }

    public void selectInstaller(InstallerInfo installerInfo, OrderRepository orderRepository, BotService botService) {
        try {
            Order oldOrder = orderRepository.findByOrderId(installerInfo.getOrderId());
            orderRepository.updateComment(installerInfo.getOrderId(), installerInfo.getInstallerComment());
            Installer installer = installerRepository.findByName(installerInfo.getInstallerFullName());
            orderRepository.updateInstaller(installer, installerInfo.getOrderId());

            Order newOrder = new Order();
            newOrder.setFullName(oldOrder.getFullName());
            newOrder.setAddress(oldOrder.getAddress());
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
            throw new RuntimeException("Ошибка выбора");
        }
    }
}
