package com.leverx.dealerst.config.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Transactional
//@PropertySource("classpath:security.properties")
public class JwtUtils {

//    @Autowired
//    private Environment env;

    public String generateToken(String username) {

        long currentTime = System.currentTimeMillis();
        Date expireDate = new Date(currentTime + (60 * 60 * 24 * 1000)); // 24 hours

        System.out.println("expireDate = " + expireDate);
        System.out.println("expireDate = " + expireDate);
        System.out.println("username = " + username);

        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, "rybak")
                .compact();

        System.out.println("jwt = " + jwt);

        return jwt;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey("rybak").parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("exception with validating. Invalid token!");
        }
        return false;
    }

    public String getUsername(String token) {

        JwtParser jwtParser = Jwts.parser().setSigningKey( "rybak" );
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

}
