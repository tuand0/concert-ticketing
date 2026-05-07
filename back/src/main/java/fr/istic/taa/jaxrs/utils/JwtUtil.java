package fr.istic.taa.jaxrs.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtUtil {
    private static final String JWT_ISSUER = "concerts-app";
    private static final long JWT_LIFETIME_SEC = 60 * 60; // 1h
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public record TokenPayload(String username, Set<String> roles) implements Principal {
        @Override
        public String getName() {
            return username;
        }
    }

    /**
     * Valide le jeton JWT et renvoie sa charge utile
     * @throws JwtException le jeton n'est pas valide, expirée, etc.
     */
    public static TokenPayload validateToken(String token) throws JwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        @SuppressWarnings("unchecked")
        List<String> rolesList = (List<String>) claims.get("roles", List.class);
        return new TokenPayload(claims.getSubject(), new HashSet<>(rolesList));
    }

    /**
     * Génère un jeton JWT à partir d'un nom d'utilisateur et de son rôle
     */
    public static String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_LIFETIME_SEC * 1_000))
                .claim("roles", roles)
                .signWith(key)
                .compact();
    }
}