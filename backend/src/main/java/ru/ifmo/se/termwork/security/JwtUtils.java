package ru.ifmo.se.termwork.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import ru.ifmo.se.termwork.domain.Authority;
import ru.ifmo.se.termwork.domain.User;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
public class JwtUtils {

    private final static String ID_CLAIM_NAME = "userId";

    private final static String ROLES_CLAIM_NAME = "roles";

    private final static int validityInMinutes = 15;

    private static SecretKey secretKey;

    @PostConstruct
    private void init(){
        //ToDo: replace with spring crypto
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public User getAuthentication(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Integer id = claims.getBody().get(ID_CLAIM_NAME, Integer.class);
            List<String> strAuthorities = (List<String>) claims.getBody().get(ROLES_CLAIM_NAME);
            HashSet<Authority> authorities = new HashSet<>();
            for(String authority : strAuthorities){
                String role = authority.substring("ROLE_".length());
                authorities.add(Role.valueOf(role).getAuthority());
            }
            return new User(id, authorities);
        } catch (JwtException e){
            throw new BadCredentialsException("Token is invalid");
        }
    }


    public String getToken(int userId, List<String> roles) {
        Date expirationDate = Date.from(Instant.now().plus(validityInMinutes, ChronoUnit.MINUTES));
        String token = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .claim(ID_CLAIM_NAME, userId)
                .claim(ROLES_CLAIM_NAME, roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return "Bearer " + token;
    }

}
