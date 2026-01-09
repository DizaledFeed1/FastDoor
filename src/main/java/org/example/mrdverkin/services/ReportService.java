package org.example.mrdverkin.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.Report;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.ReportRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.ReportDTO;

import org.example.mrdverkin.dto.ResponceDTO;
import org.example.mrdverkin.reportCreators.ExcelCreater;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ExcelCreater excelCreater;

    /**
     * Метод для генирации отчёта
     * @param reportRequest
     * @param owner
     * @return ResponceDTO
     */
    public ResponceDTO createReport(ReportDTO reportRequest, User owner) {
        ResponceDTO responceDTO = new ResponceDTO();
        User updateOwner = userRepository.findByNickname(owner.getNickname())
                .orElseThrow(EntityNotFoundException::new);

        Report report = new Report();

        report.setTitle(reportRequest.getTitle() + owner.getNickname());

        report.setDateFrom(reportRequest.getDateFrom());
        report.setDateTo(reportRequest.getDateTo());

        report.setOwner(owner);

        //добавляем данные о магазинах
        List<User> users = new ArrayList<>();
        for (String relatedUsers: reportRequest.getRelatedUsers()) {
           users.add(userRepository.findByNickname(relatedUsers).orElseThrow(EntityNotFoundException::new));
        }
        report.setRelatedUsers(users);

        LocalDateTime dateFrom = reportRequest.getDateFrom().atStartOfDay();
        LocalDateTime dateTo = reportRequest.getDateTo().atTime(LocalTime.MAX);

        //добавляем заказы
        List<Order> orders =orderRepository.findOrdersByNicknamesAndDateRange(dateFrom, dateTo,reportRequest.getRelatedUsers());
        report.setOrders(orders);

        //сейвим отчёт
        reportRepository.save(report);

        //затем сейвим юзера
        updateOwner.getReports().add(report);
        userRepository.save(updateOwner);

        responceDTO.setStatus(HttpStatus.CREATED);
        responceDTO.setMessage("Report created");

        return responceDTO;
    }

    /**
     * Метод возвращает все отчёты для определённого пользователя
     * @param user
     * @return List<ReportDTO>
     */
    public List<ReportDTO> getAllReportByUser(User user) {
        List<Report> results = reportRepository.findAllByOwner(user.getId());
        return results.stream()
                .map(r -> new ReportDTO(
                        r.getId(),
                        r.getTitle(),
                        r.getDateFrom(),
                        r.getDateTo(),
                        r.getRelatedUsers().stream()
                                .map(User::getNickname)
                                .toList(),
                        r.getDateCreated(),
                        null
                ))
                .toList();
    }

    public byte[] downloadReport(ReportDTO reportDTO) {
        return excelCreater.convertReport(reportDTO);
    }

    public ReportDTO getReportById(Long id) {
        return reportRepository.findByIdWithOrders(id)
                .map(r -> new ReportDTO(
                        r.getId(),
                        r.getTitle(),
                        r.getDateFrom(),
                        r.getDateTo(),
                        r.getRelatedUsers().stream()
                                .map(User::getNickname)
                                .toList(),
                        r.getDateCreated(),
                        r.getOrders()
                )).orElse(null);
    }
}
