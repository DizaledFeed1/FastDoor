package org.example.mrdverkin.dataBase.Entitys;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "door_limits")
public class DoorLimits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limit_date", nullable = false, unique = true)
    private Date limitDate;

    @Column(nullable = false)
    private int frontDoorQuantity;

    @Column(nullable = false)
    private int inDoorQuantity;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean availability = true;

    @OneToMany(mappedBy = "doorLimits", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();

    public boolean getAvailability() {
        return availability;
    }
}
