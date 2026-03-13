package pe.edu.epe.shp_reports_api.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name="user_shifts")
public class UserShift {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false) private User user;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Shift shift;

  private LocalDate effectiveFrom;
  private LocalDate effectiveTo;

  // getters/setters
  public Long getId(){ return id; }
  public void setId(Long id){ this.id = id; }
  public User getUser(){ return user; }
  public void setUser(User user){ this.user = user; }
  public Shift getShift(){ return shift; }
  public void setShift(Shift shift){ this.shift = shift; }
  public LocalDate getEffectiveFrom(){ return effectiveFrom; }
  public void setEffectiveFrom(LocalDate effectiveFrom){ this.effectiveFrom = effectiveFrom; }
  public LocalDate getEffectiveTo(){ return effectiveTo; }
  public void setEffectiveTo(LocalDate effectiveTo){ this.effectiveTo = effectiveTo; }
}