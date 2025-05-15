package org.example.mrdverkin.controllers.mainInstaller;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dto.InstallerInfo;
import org.example.mrdverkin.dto.OrderAttribute;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.DateAvailability;
import org.example.mrdverkin.services.MainInstallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home/mainInstaller")
/**
 * Класс котроллер для главногоустановщика.
 * @author Кирилл Селявский
 * @version 1.0
 */
public class MainInstallerController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InstallerRepository installerRepository;
    @Autowired
    private MainInstallerService mainInstallerService;
    @Autowired
    private DoorLimitsRepository doorLimitsRepository;

    @ModelAttribute("selectInstaller")
    public InstallerInfo selectInstaller() {
        return new InstallerInfo();
    }

    /**
     * Метод для выбора установщика.
     * @param installerInfo
     * @return редирект на /home/mainInstaller.
     */
    @PostMapping()
    public String addInstaller(@RequestBody InstallerInfo installerInfo) {
        orderRepository.updateComment(installerInfo.getOrderId(),installerInfo.getInstallerComment());
        orderRepository.updateInstaller(installerRepository.findByName(installerInfo.getInstallerFullName()),installerInfo.getOrderId());
        mainInstallerService.sendMessage(orderRepository.findById(installerInfo.getOrderId()).get());

        return "redirect:/home/mainInstaller";
    }

    /**
     * Метод возвращает темплейт mainInstaller.
     * @param model
     * @return mainInstaller.
     */
    @GetMapping
    public String mainInstaller(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> ordersPage = orderRepository.findByInstallerNull(pageable);
        List<DateAvailability> availabilityList = DateAvailability.fromDates(doorLimitsRepository, orderRepository);

        List<OrderAttribute> orderAttributes = OrderAttribute.fromOrderList(ordersPage);

        model.addAttribute("orders", orderAttributes);
        model.addAttribute("installers", installerRepository.findAll());
        model.addAttribute("availabilityList", availabilityList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        return "mainInstaller";
    }
}
