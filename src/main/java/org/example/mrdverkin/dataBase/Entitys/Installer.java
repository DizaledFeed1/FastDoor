package org.example.mrdverkin.dataBase.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "installer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Installer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //todo Нужно сделать поиск по id а не по fullName
//    @Convert(converter = AesGcmEncryptor.class)
    private String fullName;
    @Column(unique = true, nullable = false)
    private String phone;

    private String TgId;

    private String MaxId;

    /**
     * Для реализации установщиков как пользователей
     */
    @OneToOne()
    private User user;

    @OneToMany(mappedBy = "installer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
