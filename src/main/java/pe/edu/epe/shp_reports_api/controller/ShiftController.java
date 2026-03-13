package pe.edu.epe.shp_reports_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.epe.shp_reports_api.domain.Shift;
import pe.edu.epe.shp_reports_api.domain.UserShift;
import pe.edu.epe.shp_reports_api.service.ShiftService;

@RestController @RequestMapping("/api/shifts")
public class ShiftController {
  private final ShiftService shifts;
  public ShiftController(ShiftService shifts) { this.shifts = shifts; }

  @PostMapping("/assign/{userId}")
  @PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMIN')")
  public ResponseEntity<UserShift> assign(@PathVariable Long userId, @RequestParam Shift shift){
    return ResponseEntity.ok(shifts.assignShift(userId, shift));
  }

  @GetMapping("/me")
  @PreAuthorize("hasAnyRole('OPERATOR','SUPERVISOR','ADMIN')")
  public ResponseEntity<UserShift> myShift(Authentication a){
    return ResponseEntity.ok(shifts.myActiveShift(a.getName()));
  }
}