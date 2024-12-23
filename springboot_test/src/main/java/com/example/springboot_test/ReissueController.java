package com.example.springboot_test;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
                break;
            }
        }

        if (refresh == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰 null");
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("만료된 토큰");
        }

        String category = this.jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않는 토큰");
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccessToken = this.jwtUtil.CreateJwt("access", username, role, 5000L);

        response.addHeader("Authorization", "Bearer " + newAccessToken);
        return ResponseEntity.status(HttpStatus.OK).body("토큰 발급 성공");
    }
}
