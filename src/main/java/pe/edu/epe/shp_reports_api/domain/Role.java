package pe.edu.epe.shp_reports_api.domain;

import jakarta.persistence.*;

@Entity @Table(name="roles")
public class Role {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true)
  private String name; // ADMIN, SUPERVISOR, OPERATOR

  public Long getId(){ return id; }
  public void setId(Long id){ this.id = id; }
  public String getName(){ return name; }
  public void setName(String name){ this.name = name; }
}