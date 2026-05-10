package org.example.mrdverkin.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InstallerInfo {
    private UUID orderId;
    private String installerFullName;
    private String installerComment;
    private Long frontDoorQuantity;
    private Long inDoorQuantity;

    public InstallerInfo(UUID id, String fullName, Long frontdoorquantitysum, Long indoorquantitysum) {
        this.orderId = id;
        this.installerFullName = fullName;
        this.frontDoorQuantity = frontdoorquantitysum;
        this.inDoorQuantity = indoorquantitysum;
    }

}
