package org.example.mrdverkin.dto;

import lombok.Data;
import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class DateAvailability {
    private LocalDate date;
    private Long frontDoorQuantity;
    private Long inDoorQuantity;

    public DateAvailability(LocalDate date, Long frontDoorQuantity, Long inDoorQuantity) {
        this.date = date;
        this.frontDoorQuantity = frontDoorQuantity;
        this.inDoorQuantity = inDoorQuantity;
    }

    //Проблема: при большом количестве записей возвращаемый список будет очень большим
    //например если в сущности будет записей на год то все записи будут возвращаться даже если даты уже не ликвидны
    //как вариант удалять прошедшие даты
    public static List<DateAvailability> fromDates(DoorLimitsRepository doorLimitsRepository, OrderRepository orderRepository) {
        List<DateAvailability> availabilityList = new ArrayList<>();
        List<DoorLimits> doorLimits = doorLimitsRepository.findAll();
        for (DoorLimits doorLimit : doorLimits ) {
            DateAvailability availability = new DateAvailability(
                    doorLimit.getLimitDate().toLocalDate(),
                    Long.valueOf(doorLimit.getFrontDoorQuantity()),
                    Long.valueOf(doorLimit.getInDoorQuantity())
            );
            DateAvailability dateAvailability = orderRepository.getDoorCountsByDate(availability.getDate());
            if (dateAvailability == null) {
                availabilityList.add(availability);
            }else {
                availability.setFrontDoorQuantity(availability.getFrontDoorQuantity() - dateAvailability.getFrontDoorQuantity());
                availability.setInDoorQuantity(availability.getInDoorQuantity() - dateAvailability.getInDoorQuantity());
                availabilityList.add(availability);
            }
        }

        return availabilityList;
    }
}
