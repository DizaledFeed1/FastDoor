package org.example.mrdverkin.dataBase.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "installer")
public class Installer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    @Column(unique = true, nullable = false)
    private String phone;

    @OneToMany(mappedBy = "installer", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
