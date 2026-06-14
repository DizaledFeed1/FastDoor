package org.example.mrdverkin.services;

import lombok.AllArgsConstructor;
import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.installer.InstallerOrderResponseDto;
import org.example.mrdverkin.mapper.InstallerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InstallerListService {

    private final OrderRepository orderRepository;
    private final InstallerRepository installerRepository;

    public Page<InstallerOrderResponseDto> getSortedOrders(List<Condition> statuses, User user, Pageable pageable) {
        Installer installer = installerRepository.findByUser(user);

        Page<Order> orderPage;
        if (statuses != null && !statuses.isEmpty()) {
                orderPage = orderRepository.findByConditionInAndInstaller(statuses, installer, pageable);
        } else {
            orderPage = orderRepository.findByInstaller(installer, pageable);
        }
        return orderPage.map(InstallerMapper::orderToDto);
    }
}
