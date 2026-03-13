package pe.edu.epe.shp_reports_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.epe.shp_reports_api.domain.User;
import pe.edu.epe.shp_reports_api.domain.UserShift;

public interface UserShiftRepository extends JpaRepository<UserShift, Long> {
  Optional<UserShift> findFirstByUserAndEffectiveToIsNull(User user);
}