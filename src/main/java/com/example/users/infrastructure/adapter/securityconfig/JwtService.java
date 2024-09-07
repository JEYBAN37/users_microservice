package com.example.users.infrastructure.adapter.securityconfig;

import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.JwtPort;
import com.example.users.infrastructure.adapter.mapper.UserDboMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
@Service
@AllArgsConstructor
public class JwtService implements JwtPort {
    private final UserDboMapper userDboMappers;
    private static final String SECRET_KEY = "294A404E635266556A586E327235753878214125442A472D4B6150645367566B";

    @Override
    public String generateToken(UserDto userDto) {
        return crateToken(new HashMap<>(), userDboMappers.toDto(userDto));
    }

    public boolean isTokenValid(String token, UserDto userDto) {
        final String username = extractUsername(token);
        final Claims claims = extractAllClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return username.equals(userDboMappers.toDto(userDto).getUsername()) && roles.contains("ROLE_ADMIN");
    }


    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generate(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        return crateToken(claims,userDboMappers.toDto(userDto));

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
       Claims claims =Jwts
               .parserBuilder()
               .setSigningKey(getSignInKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
        Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date())) {
            throw new UserException("Token ha expirado");
        }
       return claims;
    }

    private String crateToken(Map<String, Object> extraClaims, @NotNull UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        extraClaims.put("roles", roles);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = hexStringToByteArray(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
