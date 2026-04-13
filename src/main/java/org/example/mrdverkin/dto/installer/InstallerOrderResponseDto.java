package org.example.mrdverkin.dto.installer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallerOrderResponseDto {

    private UUID id;

    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("front_door_count")
    private Integer frontDoorCount;

    @JsonProperty("in_door_count")
    private Integer inDoorCount;

    @JsonProperty("message_seller")
    private String messageSeller;

    @JsonProperty("message_main_installer")
    private String messageMainInstaller;

    @JsonProperty("order_date")
    private LocalDate orderDate;

    @JsonProperty("full_name")
    private String fullName;
}
