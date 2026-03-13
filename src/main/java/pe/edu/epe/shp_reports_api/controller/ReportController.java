package pe.edu.epe.shp_reports_api.controller;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.epe.shp_reports_api.domain.ReportConfig;
import pe.edu.epe.shp_reports_api.dto.ManualReportRequest;
import pe.edu.epe.shp_reports_api.dto.ReportConfigRequest;
import pe.edu.epe.shp_reports_api.repository.ReportConfigRepository;
import pe.edu.epe.shp_reports_api.service.ReportService;

import java.time.LocalDate;

@RestController @RequestMapping("/api/reports")
public class ReportController {
  private final ReportConfigRepository cfgRepo;
  private final ReportService reportService;

  public ReportController(ReportConfigRepository cfgRepo, ReportService reportService){
    this.cfgRepo = cfgRepo; this.reportService = reportService;
  }

  @PutMapping("/config")
  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
  public ReportConfig saveConfig(@Valid @RequestBody ReportConfigRequest req){
    var cfg = cfgRepo.findTopByOrderByIdAsc().orElse(new ReportConfig());
    cfg.setCron(req.cron());
    cfg.setRecipientsCsv(req.recipientsCsv());
    cfg.setFormat(ReportConfig.Format.valueOf(req.format()));
    cfg.setTimezone(req.timezone());
    return cfgRepo.save(cfg);
  }

  @GetMapping("/config")
  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
  public ReportConfig getConfig(){
    return cfgRepo.findTopByOrderByIdAsc().orElse(new ReportConfig());
  }

  @PostMapping("/manual")
  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
  public ResponseEntity<byte[]> manual(@Valid @RequestBody ManualReportRequest req){
    var from = LocalDate.parse(req.from());
    var to   = LocalDate.parse(req.to());
    byte[] body;
    String filename, contentType;

    switch (req.format()){
      case "PDF" -> { body = reportService.rangePdf(from, to); filename="reporte.pdf"; contentType="application/pdf"; }
      case "XLSX" -> { body = reportService.rangeXlsx(from, to); filename="reporte.xlsx"; contentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; }
      case "BOTH" -> { body = reportService.rangePdf(from, to); filename="reporte.pdf"; contentType="application/pdf"; }
      default -> throw new IllegalArgumentException("Formato inválido");
    }
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename)
      .contentType(MediaType.parseMediaType(contentType))
      .body(body);
  }
}