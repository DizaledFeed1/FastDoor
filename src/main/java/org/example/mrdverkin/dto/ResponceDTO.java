package org.example.mrdverkin.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponceDTO {
    private HttpStatus status;
    private String message;
}
