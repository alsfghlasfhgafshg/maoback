package com.mao.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
        static final long EXPIRATIONTIME = 432_000_000;     // 5天
        static final String SECRET = "P@ssw02d";            // JWT密码
//        static final String TOKEN_PREFIX = "Bearer";        // Token前缀
//        static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
        static Algorithm JWT_ALGORITHM = Algorithm.HMAC256(SECRET);

        // JWT生成方法
        static String generateToken (String openid) {

            // 生成JWT
            String token = JWT.create()
                    .withClaim("id",openid)
                    .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATIONTIME))
                    .sign(JWT_ALGORITHM);
            return token;
        }

        // JWT验证方法
        public Integer verifyToken(String token){
            JWTVerifier jwtVerifier = JWT.require(JWT_ALGORITHM).build();
            try{
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                return Integer.valueOf(decodedJWT.getClaim("id").asString());
            } catch (TokenExpiredException e) {
                throw new TokenExpiredException("TokenExpiredException");
            } catch (SignatureVerificationException e) {
                throw new SignatureVerificationException(JWT_ALGORITHM);
            }
        }
}
