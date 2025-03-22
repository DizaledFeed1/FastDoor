package org.example.mrdverkin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SelectInstaller {
    private Long orderId;
    private String installerFullName;

}
