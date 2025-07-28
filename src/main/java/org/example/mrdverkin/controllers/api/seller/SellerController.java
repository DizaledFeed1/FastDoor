package org.example.mrdverkin.controllers.api.seller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
@Tag(name = "Контроллер магазинов")
public class SellerController {
    @Autowired
    private SellerService sellerService;


    @Operation(description = "Метод возвращает список всех магазинов")
    @GetMapping("/all")
    public String getSeller() {
        return sellerService.getAllSellar();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleInvalidEnum(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
