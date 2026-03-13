package pe.edu.epe.shp_reports_api.dto;

import jakarta.validation.constraints.*;

public record ReportConfigRequest(
  @NotBlank String cron,
  @NotBlank String recipientsCsv,
  @NotBlank String format, // PDF|XLSX|BOTH
  @NotBlank String timezone
) {}