package com.gjf.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 9:23
 */
public class JWTUtil {

    private final static String SECRET = "sdsdsd_123_HDSUHUDIHSDHSLHDLHSLDLH";

    public static String generateToken(String id) {
        Jwts.parser().setSigningKey("").parseClaimsJws("");
        return generateToken(id, getKey());
    }

    /**
     * @param id        用户id
     * @param nickname  用户名
     * @param ttlMillis 多久后失效
     * @return token
     */
    public static String generateToken(String id, String nickname, @NotNull Long ttlMillis) {
        return Jwts.builder()
                .setId(id)
                .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
                .setSubject(nickname)
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.printBase64Binary(getKey().getBytes()))
                .compact();
    }

    public static String generateToken(String id, String nickname, @NotNull Long ttlMillis, @NotNull String key) {
        return Jwts.builder()
                .setId(id)
                .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
                .setSubject(nickname)
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.printBase64Binary(key.getBytes()))
                .compact();
    }

    private static String generateToken(String id, String key) {
        return Jwts.builder()
                .setId(id)
                .signWith(SignatureAlgorithm.HS512,
                        DatatypeConverter.printBase64Binary(key.getBytes()))
                .compact();
    }


    /**
     *
     * @param token access token
     * @return user id
     */
    public static Long parseId(String token) {
        return Long.valueOf(
                Jwts.parser()
                .setSigningKey(DatatypeConverter
                .printBase64Binary(getKey().getBytes()))
                .parseClaimsJws(token).getBody()
                .getId()
        );
    }

    public static Long parseId(String token,String key) {
        return Long.valueOf(
                Jwts.parser()
                .setSigningKey(DatatypeConverter
                .printBase64Binary(key.getBytes()))
                .parseClaimsJws(token).getBody()
                .getId()
        );
    }

    private static String getKey() {
        return SECRET;
    }
}
