package org.example.mrdverkin.controllers.api.seller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
@Tag(name = "Контроллер магазинов")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }
    //test
    @Operation(description = "Метод возвращает список всех магазинов")
    @GetMapping("/all")
    public List<String> getSeller() {
        return sellerService.getAllSeller();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleInvalidEnum(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
