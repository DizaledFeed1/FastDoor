package org.example.mrdverkin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReportRequest {
    @NotNull
    private String title;

    //Промежуток дат отчёта
    @NotNull
    private LocalDate dateFrom;
    @NotNull
    private LocalDate dateTo;

    //Магазины о которых будет отчёт
    @NotNull
    private List<String> relatedUsers;
}