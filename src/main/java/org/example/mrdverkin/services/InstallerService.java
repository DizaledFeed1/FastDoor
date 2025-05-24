package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Installer installer = new Installer();
        installer.setFullName(fullName);
        installer.setPhone(phone);
        installerRepository.save(installer);
    }

    public void updateInstaller(Long id, String fullName, String phone) {
        Installer installer = findInstallerById(id);
        installer.setFullName(fullName);
        installer.setPhone(phone);
        installerRepository.save(installer);}
}
