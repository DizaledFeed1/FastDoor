package org.example.mrdverkin.controllers.api.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "ReportController",
        description = "Контроллер для работы с отчётами")
public class ReportController {
    @Autowired
    private ReportService reportService;


    @Operation(summary = "Создать отчёт",
    description = "Создаёт отчёт с о магазинах relatedUsers, промежутке дат от dateFrom до dateTo, названием title",
    responses = {
            @ApiResponse(responseCode = "200", description = "Report created")
    })
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<String> createReport(@RequestBody @Valid ReportDTO reportDTO,
                                          @AuthenticationPrincipal User user) {

        ResponceDTO responce = reportService.createReport(reportDTO, user);

        return ResponseEntity.status(responce.getStatus()).body(responce.getMessage());
    }

    @Operation(summary = "Список отчётов",
            description = "Возвращает все данные отчёта кроме orders для владельца этих отчётов"
    )
    @GetMapping("/all")
    public ResponseEntity<List<ReportDTO>> getAllReportsByUser(@AuthenticationPrincipal User user) {
        List<ReportDTO> response = reportService.getAllReportByUser(user);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Скачивание отчёта",
            description = "Возвращает массив байт\n" +
                    "ContentDisposition: .xls\n" +
                    "ContentType: APPLICATION_OCTET_STREAM (бинарноесодержимое файла)"
    )
    @GetMapping("/download")
    public ResponseEntity<byte []> downloadReport(@RequestParam Long reportId) {
        ReportDTO reportDTO = reportService.getReportById(reportId);
        byte[] bytes  = reportService.dowloadReport(reportDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(reportDTO.getTitle() + ".xls").build());
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }
}
