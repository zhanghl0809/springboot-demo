package com.example.demo.utils;

import com.example.demo.common.enums.TokenEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * token工具类
 * 凯撒加密方式
 */
public class TokenUtil {

    private static final String SECRET = "a1g2y47dg3dj59fjhhsd7cnewy73j";


    /**
     * 初始化生成token的参数
     * @param userId userId
     * @return String
     */
    public static String generateToken(String userId) {
        Map<String, Object> map = new HashMap<>(1);
        String str = KaisaUtil.encryptKaisa(userId);
        map.put("userId", str);
        return generateToken(map);
    }

    public static String generateToken(Long userId) {
        Map<String, Object> map = new HashMap<>(1);
        String str = KaisaUtil.encryptKaisa(String.valueOf(userId));
        map.put("userId", str);
        return generateToken(map);
    }

    /**
     * 生成token
     * @param map
     * @return
     */
    private static String generateToken(Map<String, Object> map) {
        //生成token
        return Jwts.builder()
                .setClaims(map)
                .setExpiration(expirationDate())//过期时间
                .setIssuedAt(currentDate())//当前时间
                .signWith(SignatureAlgorithm.HS512, SECRET)//头部信息 第一个参数为加密方式为哈希512  第二个参数为加的盐为secret字符串
                .compact();
    }


    /**
     * 是否可以刷新token
     * @param token token
     * @param lastPasswordReset s
     * @return
     */
    public static Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            final Date iat = claims.getIssuedAt();
            final Date exp = claims.getExpiration();
            return !iat.before(lastPasswordReset) && !exp.before(currentDate());
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 获取token信息
     * @param token
     * @return
     */
    public static String getTokenId(String token) {
        String userId;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            String str = Objects.isNull(claims.get("userId")) ? null : claims.get("userId").toString();
            userId = KaisaUtil.decryptKaiser(str);
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            if (Objects.isNull(claims.get("userId"))) {
                return null;
            }
            refreshedToken = generateToken(claims.get("userId").toString());
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static String verifyToken(String token) {
        String result = "";
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            result = TokenEnum.TOKEN_VALID.getCode();
        } catch (Exception e) {
            result = TokenEnum.TOKEN_INVALID.getCode();;
        }
        return result;
    }

    private static Date expirationDate() {
        return new Date(System.currentTimeMillis() + 30 * 1000);
    }

    private static Date currentDate() {
       return new Date(System.currentTimeMillis());
    }
}