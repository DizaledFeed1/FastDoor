package org.example.mrdverkin.dataBase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String address;
    private String phone;
    private String email;


    private LocalDate dateOrder;

    @NotNull(message = "Время доставки должно быть указано")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeOrder;;

    private Date placeAt = new Date();

    @NotNull(message = "Колличество дверей не указано")
    private int quantity;
}
