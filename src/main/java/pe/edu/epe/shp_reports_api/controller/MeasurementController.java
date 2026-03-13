package pe.edu.epe.shp_reports_api.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.epe.shp_reports_api.domain.Measurement;
import pe.edu.epe.shp_reports_api.dto.MeasurementCreateRequest;
import pe.edu.epe.shp_reports_api.service.MeasurementService;

@RestController @RequestMapping("/api/measurements")
public class MeasurementController {
  private final MeasurementService service;
  public MeasurementController(MeasurementService s){ this.service = s; }

  @GetMapping("/latest")
  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','OPERATOR')")
  public Measurement latest(){ return service.latest(); }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
  public Measurement create(@Valid @RequestBody MeasurementCreateRequest req, Authentication a){
    return service.create(req, a);
  }
}