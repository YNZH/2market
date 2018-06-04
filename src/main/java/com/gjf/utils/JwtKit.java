package com.gjf.utils;

import com.gjf.config.TokenProperties;
import com.sun.prism.PixelFormat;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 9:23
 */
@Component
public class JwtKit {
    private static Logger logger = LoggerFactory.getLogger(JwtKit.class);
    public static TokenProperties tokenProperties;

    @Autowired
    private void setTokenProperties(TokenProperties tokenProperties) {
        JwtKit.tokenProperties = tokenProperties;
    }

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
                .setSubject(nickname)
                .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
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
                .signWith(SignatureAlgorithm.HS512,DatatypeConverter.printBase64Binary(key.getBytes()))
                .compact();
    }

    public static boolean isWillExpire(String token) {
        return System.currentTimeMillis() + tokenProperties.getExpireTime()/2 > parseExpireTime(token).getTime();
    }

    /**
     * @param token access token
     * @return user id
     */
    private static Date parseExpireTime(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter
                        .printBase64Binary(getKey().getBytes()))
                        .parseClaimsJws(token).getBody()
                        .getExpiration();
    }


    /**
     *
     * @param token access token
     */
    public static Claims parseClaimsBody(String token){
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.printBase64Binary(getKey().getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @param token access token
     * @return user id
     */
    public static Long parseId(String token) {
        return Long.valueOf(
                Jwts.parser()
                        .setSigningKey(DatatypeConverter.printBase64Binary(getKey().getBytes()))
                        .parseClaimsJws(token).getBody()
                        .getId()
        );
    }

    public static Long parseId(String token, String key) {
        return Long.valueOf(
                Jwts.parser()
                        .setSigningKey(DatatypeConverter
                                .printBase64Binary(key.getBytes()))
                        .parseClaimsJws(token).getBody()
                        .getId()
        );
    }

    private static String getKey() {
        return tokenProperties.getSecret();
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        /**
         * 首先从cookie取
         */
        if (request.getCookies() == null) {
            return null;
        }
        Optional<String> tokenOptional = Arrays.stream(request.getCookies())
                .filter(x -> "access_token".equalsIgnoreCase(x.getName()))
                .map(Cookie::getValue)
                .findFirst();
        if (tokenOptional.isPresent()) {
            return tokenOptional.get();
        }

        /**
         * get from HttpHeader:Authorization
         */
        String accessToken = request.getHeader("Authorization");
        if (accessToken != null && accessToken.length() > 0) {
            return accessToken.split(" ")[1];
        }
        return null;

    }
}
