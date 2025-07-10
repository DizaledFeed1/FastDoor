package org.example.mrdverkin.services;

import io.swagger.v3.oas.annotations.Operation;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.Report;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.ReportRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.ReportDTO;

import org.example.mrdverkin.dto.ResponceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Метод для генирации отчёта
     * @param reportRequest
     * @param owner
     * @return ResponceDTO
     */
    public ResponceDTO createReport(ReportDTO reportRequest, User owner) {
        ResponceDTO responceDTO = new ResponceDTO();
        User apdateowner = userRepository.findByNickname(owner.getNickname()).get();

        Report report = new Report();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(reportRequest.getTitle()).append(owner.getNickname());

        report.setTitle(stringBuilder.toString());

        report.setDateFrom(reportRequest.getDateFrom());
        report.setDateTo(reportRequest.getDateTo());

        report.setOwner(owner);

        //добавляем данные о магазинах
        List<User> users = new ArrayList<>();
        for (String relatedUsers: reportRequest.getRelatedUsers()) {
           users.add(userRepository.findByNickname(relatedUsers).get());
        }
        report.setRelatedUsers(users);

        LocalDateTime dateFrom = reportRequest.getDateFrom().atStartOfDay();
        LocalDateTime dateTo = reportRequest.getDateTo().atTime(LocalTime.MAX);

        //добавляем заказы
        List<Order> orders =orderRepository.findOrdersByNicknamesAndDateRange(dateFrom, dateTo,reportRequest.getRelatedUsers());
        System.out.println(orders.size());
        report.setOrders(orders);

        //сейвим отчёт
        reportRepository.save(report);

        //затем сейвим юзера
        apdateowner.getReports().add(report);
        userRepository.save(apdateowner);

        responceDTO.setStatus(HttpStatus.CREATED);
        responceDTO.setMessage("Report created");

        return responceDTO;
    }

    public List<ReportDTO> getAllReportByUser(User user) {
        List<Report> results = reportRepository.findAllByOwner(user.getId());
        List<ReportDTO> reportDTOS = results.stream()
                .map(r -> new ReportDTO(
                        r.getTitle(),
                        r.getDateFrom(),
                        r.getDateTo(),
                        r.getRelatedUsers().stream()
                                .map(User::getNickname)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return reportDTOS;
    }
}
