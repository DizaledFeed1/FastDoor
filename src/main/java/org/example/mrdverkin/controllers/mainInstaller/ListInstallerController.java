//package org.example.mrdverkin.controllers.mainInstaller;
//
//import org.example.mrdverkin.services.InstallerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/home/mainInstaller/listInstallers")
//public class ListInstallerController {
//    @Autowired
//    private InstallerService installerService;
//
//    @GetMapping
//    public String listInstallers(Model model) {
//        model.addAttribute("installers", installerService.getAllInstallers());
//        return "listInstallers";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteInstaller(@PathVariable Long id) {
//        installerService.deleteInstallerById(id);
//        return "redirect:/home/mainInstaller/listInstallers";
//    }
//
//    @PostMapping("/create")
//    public String createInstaller(@RequestParam String fullName, @RequestParam String phone) {
//        installerService.createInstaller(fullName, phone);
//        return "redirect:/home/mainInstaller/listInstallers";
//    }
//}
