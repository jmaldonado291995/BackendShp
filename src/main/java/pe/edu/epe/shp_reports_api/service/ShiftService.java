package pe.edu.epe.shp_reports_api.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import pe.edu.epe.shp_reports_api.domain.*;
import pe.edu.epe.shp_reports_api.repository.*;

@Service
public class ShiftService {
  private final UserRepository users;
  private final UserShiftRepository userShifts;

  public ShiftService(UserRepository users, UserShiftRepository userShifts) {
    this.users = users; this.userShifts = userShifts;
  }

  public UserShift assignShift(Long userId, Shift shift){
    var user = users.findById(userId).orElseThrow();
    userShifts.findFirstByUserAndEffectiveToIsNull(user).ifPresent(active -> {
      throw new RuntimeException("El usuario ya tiene un turno activo");
    });
    var us = new UserShift();
    us.setUser(user);
    us.setShift(shift);
    us.setEffectiveFrom(LocalDate.now());
    return userShifts.save(us);
  }

  public UserShift myActiveShift(String username){
    var user = users.findByUsername(username).orElseThrow();
    return userShifts.findFirstByUserAndEffectiveToIsNull(user).orElse(null);
  }
}