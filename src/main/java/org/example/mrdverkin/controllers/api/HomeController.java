package org.example.mrdverkin.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping("/seller")
    public ResponseEntity<Map<String, String>> getSeller() {
        return ResponseEntity.ok(Map.of("message","Seller ok"));
    }
}
