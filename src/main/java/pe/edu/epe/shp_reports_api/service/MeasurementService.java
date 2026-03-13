package pe.edu.epe.shp_reports_api.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pe.edu.epe.shp_reports_api.domain.Measurement;
import pe.edu.epe.shp_reports_api.dto.MeasurementCreateRequest;
import pe.edu.epe.shp_reports_api.repository.MeasurementRepository;

@Service
public class MeasurementService {
  private final MeasurementRepository repo;
  public MeasurementService(MeasurementRepository repo){ this.repo = repo; }

  public Measurement latest(){ return repo.findTopByOrderByTimestampDesc(); }

  public Measurement create(MeasurementCreateRequest req, Authentication a){
    var m = new Measurement();
    m.setTonIn(req.tonIn()); m.setTonOut(req.tonOut());
    m.setWaterPumpHz(req.waterPumpHz()); m.setPumpAmps(req.pumpAmps());
    m.setCyclonePressure(req.cyclonePressure());
    m.setCreatedBy(a != null ? a.getName() : "system");
    return repo.save(m);
  }
}