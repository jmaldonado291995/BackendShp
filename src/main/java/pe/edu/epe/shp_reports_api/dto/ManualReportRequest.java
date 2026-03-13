package pe.edu.epe.shp_reports_api.dto;

import jakarta.validation.constraints.*;

public record ManualReportRequest(
  @NotBlank String from, // yyyy-MM-dd
  @NotBlank String to,   // yyyy-MM-dd
  @NotBlank String format
) {}