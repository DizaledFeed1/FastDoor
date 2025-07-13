package org.example.mrdverkin.controllers.api.report;

import jakarta.validation.Valid;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dto.ReportDTO;
import org.example.mrdverkin.dto.ResponceDTO;
import org.example.mrdverkin.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;


    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> createReport(@RequestBody @Valid ReportDTO reportDTO,
                                          @AuthenticationPrincipal User user) {

        ResponceDTO responce = reportService.createReport(reportDTO, user);

        return ResponseEntity.status(responce.getStatus()).body(responce.getMessage());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReportsByUser(@AuthenticationPrincipal User user) {
        List<ReportDTO> response = reportService.getAllReportByUser(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download")
    public ResponseEntity<byte []> downloadReport(@RequestParam Long reportId) {
        ReportDTO reportDTO = reportService.getReportById(reportId);
        byte[] bytes  = reportService.dowloadReport(reportDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(reportDTO.getTitle() + ".xls").build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }
}
