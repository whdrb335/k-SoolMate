package project.k_SoolMate.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


/**
 * JWT 생성 + 검증을 담당하는 핵심 컴포넌트.
 */
@Component
public class JwtTokenProvider {

    private final String secretKey = "KSOOLMATE_JWT_SUPER_SECRET_KEY_2025_ABCDEFG";
    private final long accessTokenValidity = 1000L * 60 * 30;            // 30분
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7;  // 7일

    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    public String createAccessToken(Long userId, String role) {
        Date now = new Date();
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
