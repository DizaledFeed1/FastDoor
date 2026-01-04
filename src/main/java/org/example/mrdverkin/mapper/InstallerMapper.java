package org.example.mrdverkin.mapper;

import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dto.InstallerDto;

public class InstallerMapper {

    public static Installer toInstaller(InstallerDto dto) {
        return Installer.builder()
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .TgId(dto.getTgId())
                .MaxId(dto.getMaxId())
                .orders(dto.getOrders())
                .build();
    }

    public static InstallerDto toInstallerDto(Installer installer) {
        return InstallerDto.builder()
                .fullName(installer.getFullName())
                .phone(installer.getPhone())
                .TgId(installer.getTgId())
                .MaxId(installer.getMaxId())
                .orders(installer.getOrders())
                .build();
    }

    public static InstallerDto toInstallerDtoWithoutOrders(Installer installer) {
        return InstallerDto.builder()
                .id(installer.getId())
                .fullName(installer.getFullName())
                .phone(installer.getPhone())
                .TgId(installer.getTgId())
                .MaxId(installer.getMaxId())
                .build();
    }
}
