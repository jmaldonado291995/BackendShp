package pe.edu.epe.shp_reports_api.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Row;      // <- ESTE Row es de Apache POI (Excel)
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.edu.epe.shp_reports_api.domain.Weighing;
import pe.edu.epe.shp_reports_api.repository.WeighingRepository;

import java.io.ByteArrayOutputStream;
import java.time.*;
import java.util.List;

@Service
public class ReportService {
  private final WeighingRepository weighings;
  public ReportService(WeighingRepository weighings){ this.weighings = weighings; }

  public byte[] dailyPdf(LocalDate date) {
    var start = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
    var end = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().minusMillis(1);
    var data = weighings.findByTimestampBetween(start, end, Pageable.unpaged()).getContent();
    return buildPdf("Reporte de Pesajes - " + date, data);
  }

  public byte[] rangePdf(LocalDate from, LocalDate to) {
    var data = queryRange(from, to);
    return buildPdf("Reporte de Pesajes - " + from + " a " + to, data);
  }

  public byte[] rangeXlsx(LocalDate from, LocalDate to){
    var data = queryRange(from, to);
    return buildXlsx(from, to, data);
  }

  private List<Weighing> queryRange(LocalDate from, LocalDate to){
    var s = from.atStartOfDay(ZoneId.systemDefault()).toInstant();
    var e = to.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().minusMillis(1);
    return weighings.findByTimestampBetween(s, e, Pageable.unpaged()).getContent();
  }

  private byte[] buildPdf(String title, List<Weighing> data){
    try(var baos = new ByteArrayOutputStream()){
      var doc = new Document();
      PdfWriter.getInstance(doc, baos);
      doc.open();
      doc.add(new Paragraph(title));
      doc.add(new Paragraph("Total registros: " + data.size()));
      var table = new PdfPTable(6);
      table.addCell("Fecha"); table.addCell("Placa");
      table.addCell("Conductor"); table.addCell("Bruto");
      table.addCell("Tara"); table.addCell("Neto");
      for(var w: data){
        table.addCell(w.getTimestamp().toString());
        table.addCell(w.getVehiclePlate());
        table.addCell(w.getDriverName());
        table.addCell(String.valueOf(w.getGrossWeight()));
        table.addCell(String.valueOf(w.getTare()));
        table.addCell(String.valueOf(w.getNetWeight()));
      }
      doc.add(table);
      doc.close();
      return baos.toByteArray();
    } catch(Exception ex){ throw new RuntimeException(ex); }
  }

  private byte[] buildXlsx(LocalDate from, LocalDate to, List<Weighing> data){
    try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()){
      Sheet s = wb.createSheet("Pesajes");
      int r=0;
      Row h = s.createRow(r++); 
      h.createCell(0).setCellValue("Desde: "+from+" Hasta: "+to);
      Row header = s.createRow(r++); 
      header.createCell(0).setCellValue("Fecha");
      header.createCell(1).setCellValue("Placa");
      header.createCell(2).setCellValue("Conductor");
      header.createCell(3).setCellValue("Bruto");
      header.createCell(4).setCellValue("Tara");
      header.createCell(5).setCellValue("Neto");
      for(var w: data){
        Row row = s.createRow(r++);
        row.createCell(0).setCellValue(w.getTimestamp().toString());
        row.createCell(1).setCellValue(w.getVehiclePlate());
        row.createCell(2).setCellValue(w.getDriverName());
        row.createCell(3).setCellValue(w.getGrossWeight());
        row.createCell(4).setCellValue(w.getTare());
        row.createCell(5).setCellValue(w.getNetWeight());
      }
      for(int c=0;c<6;c++) s.autoSizeColumn(c);
      wb.write(baos);
      return baos.toByteArray();
    } catch(Exception ex){ throw new RuntimeException(ex); }
  }
}