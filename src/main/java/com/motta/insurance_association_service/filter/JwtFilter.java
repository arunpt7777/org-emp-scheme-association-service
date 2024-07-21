package com.motta.insurance_association_service.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {

                response.setContentType("application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String message = "{\r\n    \"timestamp\": \"" + LocalDateTime.now() + "\",\r\n  " +
                        "  \"status\": " + HttpStatus.UNAUTHORIZED.value() + ",\r\n " +
                        "   \"error\": \"" + HttpStatus.UNAUTHORIZED.toString() + "\",\r\n " +
                        "   \"errorDetails\": \"Authorization Failure - Okta JWT Bearer Token Required\"\r\n}";
                response.getWriter().write(message);
            } else {
                try {
                    final String token = authHeader.substring(7);
                    Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
                    Date tokenIssuedTime = claims.getIssuedAt();
                    long timeDifferece = tokenIssuedTime.getTime() - new Date().getTime();
                    long minuteDifference = timeDifferece / (1000 * 60);

                    if(minuteDifference < -60) {
                        response.setContentType("application/json");
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        String message = "{\r\n    \"timestamp\": \"" + LocalDateTime.now() + "\",\r\n  " +
                                "  \"status\": " + HttpStatus.UNAUTHORIZED.value() + ",\r\n " +
                                "   \"error\": \"" + HttpStatus.UNAUTHORIZED.toString() + "\",\r\n " +
                                "   \"errorDetails\": \"Authorization Failure - Okta JWT Bearer Token Expired\"\r\n}";
                        response.getWriter().write(message);
                    } else {
                        request.setAttribute("claims", claims);
                        request.setAttribute("blog", servletRequest.getParameter("id"));
                        filterChain.doFilter(request, response);
                    }
                } catch (MalformedJwtException e) {
                    response.setContentType("application/json");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    String message = "{\r\n    \"timestamp\": \"" + LocalDateTime.now() + "\",\r\n  " +
                            "  \"status\": " + HttpStatus.FORBIDDEN.value() + ",\r\n " +
                            "   \"error\": \"" + HttpStatus.FORBIDDEN.toString() + "\",\r\n " +
                            "   \"errorDetails\": \"Authentication Failure - Invalid JWT Bearer Token\"\r\n}";
                    response.getWriter().write(message);
                }
            }
        }
    }
}