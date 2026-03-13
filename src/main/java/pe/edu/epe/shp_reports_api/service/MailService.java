package pe.edu.epe.shp_reports_api.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import pe.edu.epe.shp_reports_api.domain.EmailLog;
import pe.edu.epe.shp_reports_api.repository.EmailLogRepository;

@Service
public class MailService {
  private final JavaMailSender mail;
  private final EmailLogRepository logs;

  public MailService(JavaMailSender mail, EmailLogRepository logs){
    this.mail = mail; this.logs = logs;
  }

  public void send(String recipientsCsv, String subject, String text, byte[] pdf, byte[] xlsx, String reportDate){
    try {
      MimeMessage msg = mail.createMimeMessage();
      var helper = new MimeMessageHelper(msg, true);
      for(String r : recipientsCsv.split(",")) if(!r.trim().isEmpty()) helper.addTo(r.trim());
      helper.setSubject(subject);
      helper.setText(text, false);
      if (pdf != null)  helper.addAttachment("reporte-"+reportDate+".pdf",  new ByteArrayResource(pdf));
      if (xlsx != null) helper.addAttachment("reporte-"+reportDate+".xlsx", new ByteArrayResource(xlsx));
      mail.send(msg);

      var log = new EmailLog(); 
      log.setRecipients(recipientsCsv); log.setSubject(subject); log.setStatus("OK"); log.setReportDate(reportDate);
      logs.save(log);
    } catch(Exception ex){
      var log = new EmailLog(); 
      log.setRecipients(recipientsCsv); log.setSubject(subject); log.setStatus("ERROR"); 
      log.setErrorMessage(ex.getMessage()); log.setReportDate(reportDate);
      logs.save(log);
      throw new RuntimeException(ex);
    }
  }
}