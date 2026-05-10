package org.example.mrdverkin.mapper;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dto.installer.InstallerOrderResponseDto;

public class InstallerMapper {

    public static InstallerOrderResponseDto orderToDto(Order order) {
        return InstallerOrderResponseDto.builder()
                .id(order.getId())
                .address(order.getAddress())
                .phoneNumber(order.getPhone())
                .frontDoorCount(order.getFrontDoorQuantity())
                .inDoorCount(order.getInDoorQuantity())
                .messageSeller(order.getMessageSeller())
                .messageMainInstaller(order.getMessageMainInstaller())
                .orderDate(order.getDateOrder())
                .fullName(order.getFullName())
                .build();
    }
}
