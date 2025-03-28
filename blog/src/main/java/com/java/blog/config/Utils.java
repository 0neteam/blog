// Utils.java
package com.java.blog.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

// 기존 코드 생략...

@Component
@RequiredArgsConstructor
public class Utils {

  private final JwtDecoder jwtDecoder;
  private final JWKSet jwkSet;

  public String getUserNo(HttpServletRequest request) {
    String userNo = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("access_token".equals(cookie.getName())) {
          String token = cookie.getValue();
          try {
            Jwt jwt = jwtDecoder.decode(token);
            userNo = (String) jwt.getClaims().get("userNo");
          } catch (JwtException e) {
            return null;
          }
        }
      }
    }
    return userNo;
  }

  // 기존 getUserName(HttpServletRequest request) 생략...

  // 추가: SecurityContextHolder를 사용하여 파라미터 없이 사용자 이름을 추출하는 메서드
  public String getUserName() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
      Object principal = auth.getPrincipal();
      if (principal instanceof UserDetails) {
        return ((UserDetails) principal).getUsername();
      } else {
        return principal.toString();
      }
    }
    return null;
  }
}
