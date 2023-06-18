package com.multree.userservice.config.jwt;

import com.multree.userservice.config.UserPrincipal;
import com.multree.userservice.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth){
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts
                .builder()
                .setSubject(auth.getUsername())
                .claim("roles", authorities)
                .claim("user_id", auth.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request)
    {
        Claims claims = extractClaims(request);

        if (claims == null)
        {
            return null;
        }

        String username = claims.getSubject();
        UUID userId = claims.get("user_id", UUID.class);

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .email(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if (username == null)
            return null;

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request){

        Claims claims = extractClaims(request);

        //String username = claims.getSubject();
        //Long userId = claims.get("user_id", Long.class);
        //UserDetails userDetails = UserPrincipal.builder()
        //        .email(username)
        //        .id(userId)
        //        .build();
        //return  (claims.equals(userDetails.getUsername())) && !isTokenExpired(request);

        if (claims == null)
            return false;

        if (claims.getExpiration().before(new Date()))
            return false;

        return true;
    }

    private boolean isTokenExpired(HttpServletRequest request) {
        return extractExpiration(request).before(new Date());
    }

    private Date extractExpiration(HttpServletRequest request) {
        return extractClaim(request, Claims::getExpiration);
    }

    private <T> T extractClaim(HttpServletRequest request, Function<Claims, T> claimsResolver){
        //String token = SecurityUtils.extractAuthTokenFromRequest(request);
        final Claims claims = extractClaims(request);
        if (claims == null)
        {
            return null;
        }
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(HttpServletRequest request)
    {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        if (token == null)
        {
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
}
