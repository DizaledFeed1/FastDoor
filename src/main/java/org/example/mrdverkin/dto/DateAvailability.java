package org.example.mrdverkin.dto;

import lombok.Data;
import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
public class DateAvailability {
    private LocalDate date;
    private Long frontDoorQuantity;
    private Long inDoorQuantity;
    private boolean available;

    public DateAvailability(LocalDate date, Long frontDoorQuantity, Long inDoorQuantity, boolean available) {
        this.date = date;
        this.frontDoorQuantity = frontDoorQuantity;
        this.inDoorQuantity = inDoorQuantity;
        this.available = available;
    }

    //Проблема: при большом количестве записей возвращаемый список будет очень большим
    //например если в сущности будет записей на год то все записи будут возвращаться даже если даты уже не ликвидны
    //как вариант удалять прошедшие даты
    public static Page<DateAvailability> fromDates(DoorLimitsRepository doorLimitsRepository, OrderRepository orderRepository, Pageable pageable) {
        Page<DoorLimits> doorLimitsPage = doorLimitsRepository.findAll(pageable);

        // Получаем все суммы заказов по датам одним запросом
        List<DateAvailability> ordersByDate = orderRepository.getDoorCountsGroupedByDate(Condition.DELETED);

        // Преобразуем в Map для быстрого поиска
        Map<LocalDate, DateAvailability> ordersByDateMap = ordersByDate.stream()
                .collect(Collectors.toMap(DateAvailability::getDate, o -> o));

        return doorLimitsPage.map(doorLimit -> {
            DateAvailability availability = new DateAvailability(
                    doorLimit.getLimitDate().toLocalDate(),
                    (long) doorLimit.getFrontDoorQuantity(),
                    (long) doorLimit.getInDoorQuantity(),
                    doorLimit.getAvailability()
            );

            // Вычитаем занятые двери, если есть заказы на эту дату
            DateAvailability ordered = ordersByDateMap.get(availability.getDate());
            if (ordered != null) {
                availability.setFrontDoorQuantity(availability.getFrontDoorQuantity() - ordered.getFrontDoorQuantity());
                availability.setInDoorQuantity(availability.getInDoorQuantity() - ordered.getInDoorQuantity());
            }

            return availability; // Возвращаем преобразованный объект
        });
    }

}
