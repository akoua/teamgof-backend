package istic.m2.project.gofback.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CredentialsUpdateInDto {
    @NotBlank(message = "old password")
    private String oldPwd;

    @NotBlank(message = "new password")
    private String newPwd;
}
