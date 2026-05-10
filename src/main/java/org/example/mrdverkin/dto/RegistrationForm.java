package org.example.mrdverkin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String confirm;

    @NotBlank
    @Size(min = 64, max = 64)
    private String inviteCode;

    public boolean isPasswordMatching() {
        return password.equals(confirm);
    }
}
