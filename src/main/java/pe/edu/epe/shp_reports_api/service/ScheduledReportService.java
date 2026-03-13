package pe.edu.epe.shp_reports_api.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pe.edu.epe.shp_reports_api.domain.ReportConfig;
import pe.edu.epe.shp_reports_api.repository.ReportConfigRepository;

import java.time.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ScheduledReportService {
  private final ReportConfigRepository cfgRepo;
  private final ReportService reports;
  private final MailService mail;

  private final AtomicLong lastRunEpochMinute = new AtomicLong(-1);

  public ScheduledReportService(ReportConfigRepository cfgRepo, ReportService reports, MailService mail){
    this.cfgRepo = cfgRepo; this.reports = reports; this.mail = mail;
  }

  // Corre cada minuto y decide si enviar según hh:mm del "cron" (formato "0 mm HH * * *")
  @Scheduled(cron = "0 * * * * *")
  public void tick(){
    var opt = cfgRepo.findTopByOrderByIdAsc();
    if(opt.isEmpty()) return;
    ReportConfig cfg = opt.get();
    if(!cfg.isActive()) return;

    var zone = ZoneId.of(cfg.getTimezone());
    var now = ZonedDateTime.now(zone);
    var epochMinute = now.toEpochSecond() / 60;
    if (lastRunEpochMinute.get() == epochMinute) return;

    try {
      var parts = cfg.getCron().split("\\s+"); // "0 mm HH * * *"
      int hh = Integer.parseInt(parts[2]);
      int mm = Integer.parseInt(parts[1]);
      if (now.getHour()==hh && now.getMinute()==mm){
        LocalDate reportDate = now.toLocalDate().minusDays(1);
        byte[] pdf=null, xlsx=null;
        switch (cfg.getFormat()){
          case PDF ->  pdf  = reports.dailyPdf(reportDate);
          case XLSX -> xlsx = reports.rangeXlsx(reportDate, reportDate);
          case BOTH -> { pdf = reports.dailyPdf(reportDate); xlsx = reports.rangeXlsx(reportDate, reportDate); }
        }
        mail.send(cfg.getRecipientsCsv(), "Reporte Diario - "+reportDate, "Adjunto reporte diario.", pdf, xlsx, reportDate.toString());
        lastRunEpochMinute.set(epochMinute);
      }
    } catch(Exception ignored){}
  }
}