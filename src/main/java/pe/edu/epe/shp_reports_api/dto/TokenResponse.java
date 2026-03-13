package pe.edu.epe.shp_reports_api.dto;

public record TokenResponse(String token, long expiresInSeconds) {}