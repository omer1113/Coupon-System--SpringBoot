package com.jb.Authorization;


import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTUtil {
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();

    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";

    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), this.signatureAlgorithm) ;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("password", userDetails.getPassword());
        claims.put("userType", userDetails.getClientType());
        //claims.put("email",userDetails.getEmail());
        String myToken = createToken(claims, userDetails.getEmail());
        return myToken;
    }


    private String createToken(Map<String, Object> claims, String subject) {
        Instant now = Instant.now();
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }


    private Claims extractAllClaims(String token) throws ExpiredJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String extractUserEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
