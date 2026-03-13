package pe.edu.epe.shp_reports_api.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "email_logs")
public class EmailLog {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant sentAt = Instant.now();
  private String recipients;
  private String subject;
  private String status; // OK | ERROR

  @Column(length = 2000)
  private String errorMessage;

  private String reportDate; // yyyy-MM-dd

  public Long getId() { return id; }
  public Instant getSentAt() { return sentAt; }
  public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
  public String getRecipients() { return recipients; }
  public void setRecipients(String recipients) { this.recipients = recipients; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public String getErrorMessage() { return errorMessage; }
  public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
  public String getReportDate() { return reportDate; }
  public void setReportDate(String reportDate) { this.reportDate = reportDate; }
}