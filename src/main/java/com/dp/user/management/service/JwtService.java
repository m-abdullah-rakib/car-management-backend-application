package com.dp.user.management.service;

import com.dp.user.management.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "UvBraZJCirOnWFdx1KgH/nML1Xnr1KB+Yeac88j7SswOpYW4zzF7XJV78QAfMUzr4KPGBMiz2qr0iRitcaV/Efu+n+1Thd5jOqyw5L1iNYc9Cv/Bnr/hbGZc+Eq0JAE+HOVLI8Pu5lRABLioeUwZz409J3P26eFzvL2MmN1eK3/LhOMXiiOCTHmXFSG8gLYHqjNeIcvtW1S04Iru3bJAEBjb3GwirJFQnMk1pusrkDOP+XGFYqblXrGtIw7fyfl4xdA66330mI4ep18PYYY+ZFDwiLKekrHsrmJXQ/VogUzlMRFPqbRHOoKU1dQ3Y2ETxwAgDpUDUSRnoBuhrVrYtu1y8pQlj1U9xl2aojzwUnw=";

    public boolean isTokenValid(String token, UserDetails userDetails) { // Seven
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) { // Eight
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) { // Nine
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) { // One
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // Four
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) { // Two
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() { // Three
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) { // Five
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(User user) { // Six
        return generateToken(new HashMap<>(), user);
    }

}
