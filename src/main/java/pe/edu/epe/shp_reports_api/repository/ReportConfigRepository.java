package pe.edu.epe.shp_reports_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.epe.shp_reports_api.domain.ReportConfig;

public interface ReportConfigRepository extends JpaRepository<ReportConfig, Long> {
  Optional<ReportConfig> findTopByOrderByIdAsc();
}