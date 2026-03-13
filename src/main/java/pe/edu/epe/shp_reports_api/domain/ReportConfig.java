package pe.edu.epe.shp_reports_api.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "report_config")
public class ReportConfig {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private boolean active = true;

  @Column(nullable = false)
  private String cron = "0 0 06 * * *"; // 06:00

  @Column(length = 2000)
  private String recipientsCsv;

  @Enumerated(EnumType.STRING)
  private Format format = Format.PDF; // PDF | XLSX | BOTH

  private String timezone = "America/Lima";

  public enum Format { PDF, XLSX, BOTH }

  public Long getId() { return id; }
  public boolean isActive() { return active; }
  public void setActive(boolean active) { this.active = active; }
  public String getCron() { return cron; }
  public void setCron(String cron) { this.cron = cron; }
  public String getRecipientsCsv() { return recipientsCsv; }
  public void setRecipientsCsv(String recipientsCsv) { this.recipientsCsv = recipientsCsv; }
  public Format getFormat() { return format; }
  public void setFormat(Format format) { this.format = format; }
  public String getTimezone() { return timezone; }
  public void setTimezone(String timezone) { this.timezone = timezone; }
}