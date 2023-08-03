package com.group10.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.User;

/**
 * Handles the generation and verification of JWT tokens.
 */
@Component
public class JWTTokenHandler {
    
    @Value("${secret.key}")
    private String secretKey;

    /**
     * Generates a JWT (JSON Web Token) for the given user.
     *
     * @param user The user for whom the token is being generated.
     * @return The generated JWT token.
     */
    public String generateJWTToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
            .withClaim("email", user.getEmail())
            .withClaim("userId", user.getUserId())
            .withClaim("isVendor", user.getVendor())
            .sign(algorithm);
        return token;
    }

    /**
     * Decodes a JWT token and verifies its authenticity using the provided secret key.
     *
     * @param token The JWT token to decode and verify.
     * @return The decoded JWT object.
     * @throws JWTVerificationException If the token is invalid or cannot be verified.
     */
    public DecodedJWT decodeJWTToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(token);
            return jwt;
            // use the userId to pull specific info
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid Token");
        }
    }
}
