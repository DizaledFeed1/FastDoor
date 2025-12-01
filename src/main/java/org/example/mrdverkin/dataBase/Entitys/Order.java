package org.example.mrdverkin.dataBase.Entitys;


import jakarta.persistence.*;
import lombok.Data;
import org.example.mrdverkin.dataBase.AesGcmEncryptor;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "\"order\"")
/**
 * Класс сущность заказов
 * @author Селявский Кирилл
 * @version 1.0
 */
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = AesGcmEncryptor.class)
    private String fullName;

    @Convert(converter = AesGcmEncryptor.class)
    private String address;
    private String phone;
    private String messageSeller;
    private String messageMainInstaller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "door_limits_id")
    private DoorLimits doorLimits;

    @Column(name = "date_order")
    private LocalDate dateOrder;

    private Date placeAt = new Date();

    @Column(name = "FRONTDOORQUANTITY")
    private int frontDoorQuantity;

    @Column(name = "INDOORQUANTITY")
    private int inDoorQuantity;

    @ManyToOne
    private Installer installer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'ACTIVE'")
    private Condition condition = Condition.ACTIVE;
}

