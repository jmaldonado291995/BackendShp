package pe.edu.epe.shp_reports_api.dto;

import jakarta.validation.constraints.*;

public record WeighingCreateRequest(
  @NotBlank String vehiclePlate,
  @NotBlank String driverName,
  @NotNull Double grossWeight,
  @NotNull Double tare,
  @NotNull Double netWeight,
  @NotBlank String direction // IN | OUT
) {}