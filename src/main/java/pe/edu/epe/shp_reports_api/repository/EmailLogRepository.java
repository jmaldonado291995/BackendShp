package pe.edu.epe.shp_reports_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.epe.shp_reports_api.domain.EmailLog;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> { }