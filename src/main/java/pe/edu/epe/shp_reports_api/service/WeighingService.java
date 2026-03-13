package pe.edu.epe.shp_reports_api.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pe.edu.epe.shp_reports_api.domain.Weighing;
import pe.edu.epe.shp_reports_api.dto.WeighingCreateRequest;
import pe.edu.epe.shp_reports_api.repository.WeighingRepository;
import java.time.*;

@Service
public class WeighingService {
  private final WeighingRepository repo;
  public WeighingService(WeighingRepository repo){ this.repo = repo; }

  public Weighing create(WeighingCreateRequest r, String actor){
    var w = new Weighing();
    w.setVehiclePlate(r.vehiclePlate());
    w.setDriverName(r.driverName());
    w.setGrossWeight(r.grossWeight());
    w.setTare(r.tare());
    w.setNetWeight(r.netWeight());
    w.setDirection(Weighing.Direction.valueOf(r.direction()));
    w.setCreatedBy(actor);
    return repo.save(w);
  }

  public Page<Weighing> findBetween(String from, String to, Pageable p){
    var start = LocalDate.parse(from).atStartOfDay(ZoneId.systemDefault()).toInstant();
    var end = LocalDate.parse(to).plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().minusMillis(1);
    return repo.findByTimestampBetween(start, end, p);
  }
}