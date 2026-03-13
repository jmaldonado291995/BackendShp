package pe.edu.epe.shp_reports_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShpReportsApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(ShpReportsApiApplication.class, args);
  }
}