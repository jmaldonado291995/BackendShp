package pe.edu.epe.shp_reports_api.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="weighings")
public class Weighing {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant timestamp = Instant.now();
  private String vehiclePlate;
  private String driverName;
  private Double grossWeight;
  private Double tare;
  private Double netWeight;

  @Enumerated(EnumType.STRING)
  private Direction direction; // IN | OUT

  private String createdBy;

  public enum Direction { IN, OUT }

  // getters/setters
  public Long getId(){ return id; }
  public Instant getTimestamp(){ return timestamp; }
  public void setTimestamp(Instant timestamp){ this.timestamp = timestamp; }
  public String getVehiclePlate(){ return vehiclePlate; }
  public void setVehiclePlate(String vehiclePlate){ this.vehiclePlate = vehiclePlate; }
  public String getDriverName(){ return driverName; }
  public void setDriverName(String driverName){ this.driverName = driverName; }
  public Double getGrossWeight(){ return grossWeight; }
  public void setGrossWeight(Double grossWeight){ this.grossWeight = grossWeight; }
  public Double getTare(){ return tare; }
  public void setTare(Double tare){ this.tare = tare; }
  public Double getNetWeight(){ return netWeight; }
  public void setNetWeight(Double netWeight){ this.netWeight = netWeight; }
  public Direction getDirection(){ return direction; }
  public void setDirection(Direction direction){ this.direction = direction; }
  public String getCreatedBy(){ return createdBy; }
  public void setCreatedBy(String createdBy){ this.createdBy = createdBy; }
}