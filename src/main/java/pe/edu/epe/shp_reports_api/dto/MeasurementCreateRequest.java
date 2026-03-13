package pe.edu.epe.shp_reports_api.dto;

import jakarta.validation.constraints.*;

public record MeasurementCreateRequest(
  @NotNull Double tonIn,
  @NotNull Double tonOut,
  @NotNull Double waterPumpHz,
  @NotNull Double pumpAmps,
  @NotNull Double cyclonePressure
) {}