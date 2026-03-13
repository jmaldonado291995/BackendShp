package pe.edu.epe.shp_reports_api.service;

import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.epe.shp_reports_api.domain.Role;
import pe.edu.epe.shp_reports_api.domain.User;
import pe.edu.epe.shp_reports_api.dto.CreateUserRequest;
import pe.edu.epe.shp_reports_api.dto.TokenResponse;
import pe.edu.epe.shp_reports_api.repository.RoleRepository;
import pe.edu.epe.shp_reports_api.repository.UserRepository;
import pe.edu.epe.shp_reports_api.security.JwtService;

@Service
public class AuthService {
  private final UserRepository users;
  private final RoleRepository roles;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthService(UserRepository users, RoleRepository roles, PasswordEncoder encoder, JwtService jwt) {
    this.users = users; this.roles = roles; this.encoder = encoder; this.jwt = jwt;
  }

  public TokenResponse login(String username, String rawPassword) {
    var user = users.findByUsername(username)
        .filter(User::isEnabled)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado o deshabilitado"));

    if (!encoder.matches(rawPassword, user.getPasswordHash()))
      throw new RuntimeException("Credenciales inválidas");

    var roleNames = user.getRoles().stream().map(Role::getName).toList();
    String token = jwt.generate(username, Map.of("roles", roleNames));
    long expSec = 120 * 60;
    return new TokenResponse(token, expSec);
  }

  public User createUser(CreateUserRequest req, String creatorRole) {
    if ("SUPERVISOR".equals(creatorRole) && !"OPERATOR".equals(req.role()))
      throw new RuntimeException("Supervisor solo puede crear operadores");
    if (users.existsByUsername(req.username())) throw new RuntimeException("username ya existe");
    if (users.existsByEmail(req.email())) throw new RuntimeException("email ya existe");

    var role = roles.findByName(req.role()).orElseThrow(() -> new RuntimeException("Rol inválido"));
    var newUser = new User();
    newUser.setUsername(req.username());
    newUser.setEmail(req.email());
    newUser.setPasswordHash(encoder.encode(req.password()));
    newUser.getRoles().add(role);
    return users.save(newUser);
  }
}