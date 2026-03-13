package pe.edu.epe.shp_reports_api.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
  @NotBlank String username,
  @NotBlank String password
) {}