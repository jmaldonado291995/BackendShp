package pe.edu.epe.shp_reports_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.epe.shp_reports_api.domain.Weighing;
import java.time.Instant;

public interface WeighingRepository extends JpaRepository<Weighing, Long> {
  Page<Weighing> findByTimestampBetween(Instant from, Instant to, Pageable pageable);
}