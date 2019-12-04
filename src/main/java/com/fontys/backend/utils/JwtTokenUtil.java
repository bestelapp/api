package com.fontys.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fontys.backend.entities.User;

public class JwtTokenUtil {

    private static String publicKey = "{{SECRET}}";
    private static Algorithm algorithm = Algorithm.HMAC256(publicKey);

    public static String generateToken(User user) {

        String token = JWT.create()
                .withIssuer(String.valueOf(user.getId()))
                .withSubject(user.getName())
                .sign(algorithm);

        return "{ \"token\": \"" + token  + "\" }";
    }

    public static User decodeToken(String jwtToken) {
        if (jwtToken != null && !jwtToken.equals("null")) {
            JWTVerifier verifier = JWT.require(algorithm).build();

            try {
                DecodedJWT decodedJWT = verifier.verify(jwtToken);
                User user = null;
                if (decodedJWT != null) {
                    user = new User(Integer.parseInt(decodedJWT.getIssuer()), decodedJWT.getSubject());
                }
                return user;
            } catch (SignatureVerificationException e) {
                return null;
            }
        }
        return null;
    }
}
