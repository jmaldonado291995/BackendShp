package pe.edu.epe.shp_reports_api.bootstrap;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.edu.epe.shp_reports_api.domain.Role;
import pe.edu.epe.shp_reports_api.domain.User;
import pe.edu.epe.shp_reports_api.repository.RoleRepository;
import pe.edu.epe.shp_reports_api.repository.UserRepository;

@Component
public class DataLoader {

  private final RoleRepository roles;
  private final UserRepository users;
  private final PasswordEncoder encoder;

  public DataLoader(RoleRepository roles, UserRepository users, PasswordEncoder encoder) {
    this.roles = roles; this.users = users; this.encoder = encoder;
  }

  @PostConstruct
  public void init(){
    createRole("ADMIN"); createRole("SUPERVISOR"); createRole("OPERATOR");

    if (users.findByUsername("admin").isEmpty()){
      var admin = new User();
      admin.setUsername("admin");
      admin.setEmail("admin@local");
      admin.setPasswordHash(encoder.encode("admin123"));
      admin.getRoles().add(roles.findByName("ADMIN").orElseThrow());
      users.save(admin);
    }
  }

  private void createRole(String name){
    roles.findByName(name).orElseGet(() -> {
      var r = new Role(); r.setName(name); return roles.save(r);
    });
  }
}