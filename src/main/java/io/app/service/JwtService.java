package io.app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${credentials.jwt.secret}")
    private String JWT_SECRET;


    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        String username=extractUsername(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims,T> resolver){
        Claims claims=extractClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    private String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.YEAR,100);
        Date expirationDate=calendar.getTime();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSigninKey())
                .compact();
    }

    private SecretKey getSigninKey(){
        byte key[]= Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(key);
    }


}
