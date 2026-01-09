package org.example.mrdverkin.service;

import org.example.mrdverkin.MrDverkinApplication;
import org.example.mrdverkin.dto.ReportDTO;
import org.example.mrdverkin.reportCreators.ExcelCreater;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = MrDverkinApplication.class)
@ActiveProfiles("dev")
public class ExcelCreateTest {

    @Autowired
    private ExcelCreater excelCreater;

    @Test
    public void convertExcel() {
        ReportDTO report = ReportDTO.builder()
                .id(Long.MAX_VALUE)
                .title("Report Title")
                .dateFrom(LocalDate.MIN)
                .dateTo(LocalDate.MAX)
                .relatedUsers(List.of("user1", "user2"))
                .dateCreated(Date.from(Instant.now()))
                .orders(List.of())
                .build();

        byte[] result = excelCreater.convertReport(report);

        assertNotNull(result);
    }
}
