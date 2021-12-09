package cn.woyun.anov.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * JWT的工具类。
 *
 * @author xuepeng
 */
public class JWTUtil {

    /**
     * 私有构造函数。
     */
    private JWTUtil() {
    }

    /**
     * 创建一个JWT。
     *
     * @param claims 信息。
     * @param secret 算法密钥。
     * @return JWT字符串。
     */
    public static String create(final Map<String, Object> claims, final String secret) {
        if (MapUtils.isEmpty(claims)) {
            throw new NullPointerException("创建JWT时，claims不能为空。");
        }
        if (StringUtils.isEmpty(secret)) {
            throw new NullPointerException("创建JWT时，secret不能为空。");
        }
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        return Jwts.builder().setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, secret)
                .compact();
    }

    /**
     * 解码token获取信息。
     *
     * @param token  用户令牌。
     * @param secret 算法密钥。
     * @param key    信息键。
     * @return 信息值。
     */
    public static <T> T get(final String token, final String secret, final String key, final Class<T> clazz) {
        if (StringUtils.isEmpty(token)) {
            throw new NullPointerException("获取JWT信息时，token不能为空。");
        }
        if (StringUtils.isEmpty(secret)) {
            throw new NullPointerException("获取JWT信息时，secret不能为空。");
        }
        if (StringUtils.isEmpty(key)) {
            throw new NullPointerException("获取JWT信息时，key不能为空。");
        }
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(key, clazz);
    }

    /**
     * 判断用户令牌是否为指定信息。
     *
     * @param token  用户令牌。
     * @param secret 算法密钥。
     * @param key    信息键。
     * @param value  信息值。
     * @return 是否为指定信息。
     */
    public static <T> boolean verify(final String token,
                                     final String secret,
                                     final String key,
                                     final T value,
                                     final Class<T> clazz) {
        return value.equals(get(token, secret, key, clazz));
    }

}
