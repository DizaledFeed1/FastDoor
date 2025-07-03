package org.example.mrdverkin.dto;

import lombok.Data;

@Data
public class InstallerInfo {
    private Long orderId;
    private String installerFullName;
    private String installerComment;
    private int frontDoorQuantity;
    private int inDoorQuantity;

}
