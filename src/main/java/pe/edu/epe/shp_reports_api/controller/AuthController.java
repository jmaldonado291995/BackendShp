package pe.edu.epe.shp_reports_api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.epe.shp_reports_api.dto.*;
import pe.edu.epe.shp_reports_api.service.AuthService;
import pe.edu.epe.shp_reports_api.domain.User;

@RestController @RequestMapping("/api/auth")
public class AuthController {
  private final AuthService auth;
  public AuthController(AuthService auth){ this.auth = auth; }

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req){
    return ResponseEntity.ok(auth.login(req.username(), req.password()));
  }

  @PostMapping("/register")
  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
  public ResponseEntity<User> register(@Valid @RequestBody CreateUserRequest req, Authentication a){
    String creatorRole = a.getAuthorities().iterator().next().getAuthority().replace("ROLE_","");
    return ResponseEntity.ok(auth.createUser(req, creatorRole));
  }
}