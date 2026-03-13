package pe.edu.epe.shp_reports_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.epe.shp_reports_api.domain.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
  Measurement findTopByOrderByTimestampDesc();
}