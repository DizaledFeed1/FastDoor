package org.example.mrdverkin.dto;

import lombok.Data;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public static List<DateAvailability> fromDates(OrderRepository orderRepository) {
        List<DateAvailability> availabilityList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 14; i++ ){
//            DateAvailability availability = new DateAvailability(today, orderRepository.numberOfFrontDoorsToInstallation(today),orderRepository.numberOfInDoorsToInstallation(today));
//            availabilityList.add(availability);
            today = today.plusDays(1);
        }

        return availabilityList;
    }
}
