package io.yorkecao.jwtauth.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author Yorke
 */
public class JwtUtils {

    public static String signJwt(String username, String secret) {
        String jwtStr = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer("yorke")
                    .withIssuedAt(new Date())
                    .withClaim("username", username);

            jwtStr = builder.sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return jwtStr;
    }

    public static String verifyJwt(String jwtStr, String secret) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("yorke")
                .build();
        DecodedJWT jwt = verifier.verify(jwtStr);
        return jwt.getClaim("username").asString();
    }
}
