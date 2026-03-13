package pe.edu.epe.shp_reports_api.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="measurements")
public class Measurement {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant timestamp = Instant.now();
  private Double tonIn;
  private Double tonOut;
  private Double waterPumpHz;
  private Double pumpAmps;
  private Double cyclonePressure;

  @Column(length=50) private String createdBy;

  // getters/setters
  public Long getId(){ return id; }
  public Instant getTimestamp(){ return timestamp; }
  public void setTimestamp(Instant t){ this.timestamp = t; }
  public Double getTonIn(){ return tonIn; }
  public void setTonIn(Double tonIn){ this.tonIn = tonIn; }
  public Double getTonOut(){ return tonOut; }
  public void setTonOut(Double tonOut){ this.tonOut = tonOut; }
  public Double getWaterPumpHz(){ return waterPumpHz; }
  public void setWaterPumpHz(Double waterPumpHz){ this.waterPumpHz = waterPumpHz; }
  public Double getPumpAmps(){ return pumpAmps; }
  public void setPumpAmps(Double pumpAmps){ this.pumpAmps = pumpAmps; }
  public Double getCyclonePressure(){ return cyclonePressure; }
  public void setCyclonePressure(Double cyclonePressure){ this.cyclonePressure = cyclonePressure; }
  public String getCreatedBy(){ return createdBy; }
  public void setCreatedBy(String createdBy){ this.createdBy = createdBy; }
}