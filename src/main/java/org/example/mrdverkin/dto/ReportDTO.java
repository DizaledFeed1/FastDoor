package org.example.mrdverkin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.mrdverkin.dataBase.Entitys.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class ReportDTO {
    private Long id;

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

    private Date dateCreated;

    private List<Order> orders;
}