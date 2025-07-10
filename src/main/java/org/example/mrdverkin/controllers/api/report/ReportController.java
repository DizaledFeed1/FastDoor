package org.example.mrdverkin.controllers.api.report;

import jakarta.validation.Valid;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dto.ReportRequest;
import org.example.mrdverkin.dto.ResponceDTO;
import org.example.mrdverkin.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> createReport(@RequestBody @Valid ReportRequest reportRequest,
                                          @AuthenticationPrincipal User user) {

        ResponceDTO responce = reportService.createReport(reportRequest, user);

        return ResponseEntity.status(responce.getStatus()).body(responce.getMessage());
    }
}
