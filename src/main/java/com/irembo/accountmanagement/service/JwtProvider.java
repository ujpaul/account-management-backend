package com.irembo.accountmanagement.service;/*
 *Reference https://www.codota.com/code/java/methods/io.jsonwebtoken.Jwts/parser
 *
 */

/**
 * @author user
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.irembo.accountmanagement.utilities.GeneralLogger;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtProvider {

    private static Logger logger = LoggerFactory.getLogger(GeneralLogger.class);

    private static String SECRET_KEY = "RND!@#$paul2022";

    public boolean isValidToken(String token) {
        try {
            
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);

            if (getUserName(token).trim().length() > 0) {
                return true;
            }

            return false;
        } catch (SignatureException e) {
            logger.info("Invalid JWT signature.");
            logger.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            logger.info("Invalid JWT token.");
            logger.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            logger.info("Expired JWT token.");
            logger.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.info("Unsupported JWT token.");
            logger.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            logger.info("JWT token compact of handler are invalid.");
            logger.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public String createJwtToken(String username) {

        try {

            String token = Jwts.builder().setSubject(username)
                    .setIssuer("paul@test.com")
                    .setIssuedAt(new Date())
                    .setId(UUID.randomUUID().toString())
                    .setExpiration(new Date(System.currentTimeMillis() + (1 * 60 * 60 * 1000)))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();

            logger.info("Logged In user: " + username);

            return token;

        } catch (Exception ex) {
            logger.error("ERROR:" + ex.getMessage());
            return "";
        }

    }



    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error(token + "|" + getStackString(e));
        }
        return claims;
    }

   private boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }

    public  Date getExpiryDate(String token){
        try {
            DecodedJWT decodedJWT = JWT.decode(token);//  JWTVerifier.verify(token);
            return  decodedJWT.getExpiresAt();
        } catch (JWTVerificationException e) {
            // ...
        }
        return  null;
    }

    private boolean isTokenValid(String token) {
        Claims claims = getClaimsFromToken(token);

        if (claims != null)
            return true;
        else
            return false;
    }

    public String getUserName(String token) {
        String username = "";
        Claims claims = getClaimsFromToken(token);

        if (claims != null) {
            username = claims.getSubject();
        }


        return username;
    }

    public String getStackString(Exception ex) {
        String errorStackTrace = "";
        StackTraceElement[] stack = ex.getStackTrace();
        for (StackTraceElement stack1 : stack) {
            if (errorStackTrace.length() == 0) {
                errorStackTrace = errorStackTrace + stack1.toString();
            } else {
                errorStackTrace = errorStackTrace + "|" + stack1.toString();
            }
        }
        return errorStackTrace;

    }
}
