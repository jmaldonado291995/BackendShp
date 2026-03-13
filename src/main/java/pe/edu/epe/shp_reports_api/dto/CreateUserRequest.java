package pe.edu.epe.shp_reports_api.dto;

import jakarta.validation.constraints.*;

public record CreateUserRequest(
  @NotBlank String username,
  @Email @NotBlank String email,
  @NotBlank String password,
  @NotBlank String role // ADMIN|SUPERVISOR|OPERATOR
) {}