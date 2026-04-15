package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.OrderAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderAnnotationRepository extends JpaRepository<OrderAnnotation, UUID> {
}
