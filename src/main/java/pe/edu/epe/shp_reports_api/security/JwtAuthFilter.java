package pe.edu.epe.shp_reports_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  public JwtAuthFilter(JwtService jwtService){ this.jwtService = jwtService; }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String auth = request.getHeader("Authorization");
    if (auth != null && auth.startsWith("Bearer ")) {
      String token = auth.substring(7);
      try {
        var jws = jwtService.parse(token);
        String username = jws.getBody().getSubject();
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) jws.getBody().get("roles");
        List<GrantedAuthority> auths = roles.stream()
            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
            .collect(Collectors.toList());
        var authentication = new UsernamePasswordAuthenticationToken(username, null, auths);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception ignored) {}
    }
    filterChain.doFilter(request, response);
  }
}