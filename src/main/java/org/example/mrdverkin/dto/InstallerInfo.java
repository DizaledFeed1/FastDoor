package org.example.mrdverkin.dto;

import lombok.Data;

@Data
public class InstallerInfo {
    private Long orderId;
    private String installerFullName;
    private String installerComment;
    private Long frontDoorQuantity;
    private Long inDoorQuantity;

    public InstallerInfo(Long id, String fullName, Long frontdoorquantitysum, Long indoorquantitysum) {
        this.orderId = id;
        this.installerFullName = fullName;
        this.frontDoorQuantity = frontdoorquantitysum;
        this.inDoorQuantity = indoorquantitysum;
    }

}
