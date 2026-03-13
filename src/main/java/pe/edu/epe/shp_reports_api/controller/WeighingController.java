package pe.edu.epe.shp_reports_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.epe.shp_reports_api.domain.Weighing;
import pe.edu.epe.shp_reports_api.dto.WeighingCreateRequest;
import pe.edu.epe.shp_reports_api.service.WeighingService;

@RestController @RequestMapping("/api/weighings")
public class WeighingController {
  private final WeighingService service;
  public WeighingController(WeighingService s){ this.service = s; }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
  public Weighing create(@Valid @RequestBody WeighingCreateRequest req, Authentication a){
    return service.create(req, a.getName());
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
  public Page<Weighing> search(@RequestParam String from, @RequestParam String to,
                               @RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue="20") int size){
    return service.findBetween(from, to, PageRequest.of(page, size, Sort.by("timestamp").descending()));
  }
}