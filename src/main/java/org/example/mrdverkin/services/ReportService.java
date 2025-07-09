package org.example.mrdverkin.services;

import io.swagger.v3.oas.annotations.Operation;
import org.example.mrdverkin.dataBase.Entitys.Report;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.ReportRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.ReportRequest;
import org.example.mrdverkin.dto.ResponceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponceDTO createReport(ReportRequest reportRequest, User owner) {
        ResponceDTO responceDTO = new ResponceDTO();

        Report report = new Report();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(reportRequest.getTitle()).append(owner.getNickname());
        report.setTitle(stringBuilder.toString());
        report.setDateFrom(reportRequest.getDateFrom());
        report.setDateTo(reportRequest.getDateTo());
        //добавляем данные о магазинах
        List<User> users = new ArrayList<>();
        for (String relatedUsers: reportRequest.getRelatedUsers()) {
           users.add(userRepository.findByNickname(relatedUsers).get());
        }
        report.setRelatedUsers(users);

        report.setOwner(owner);

        return null;
    }
}
