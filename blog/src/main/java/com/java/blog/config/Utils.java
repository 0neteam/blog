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

  // 추가: JWT 토큰에서 사용자 이름(name) 클레임을 추출하는 메서드
  public String getUserName(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("access_token".equals(cookie.getName())) {
          String token = cookie.getValue();
          try {
            Jwt jwt = jwtDecoder.decode(token);
            String userName = (String) jwt.getClaims().get("name");
            if(userName == null || userName.isBlank()){
              userName = (String) jwt.getClaims().get("sub");
            }
            System.out.println("추출된 userName: " + userName);
            return userName;
          } catch (JwtException e) {
            e.printStackTrace();
            return null;
          }
        }
      }
    }
    return null;
  }


}
