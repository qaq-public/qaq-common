package com.qaq.base.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
public class JWTVerifierComponent {

    private final JWTVerifier jwtVerifier;

    public JWTVerifierComponent(@NotBlank @NotNull String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {

        PublicKey publicKey = getPublicKeyFromString(publicKeyStr);
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

        this.jwtVerifier = JWT.require(algorithm).withClaimPresence("email").build();
    }

    private PublicKey getPublicKeyFromString(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Remove the header and footer and newlines
        String publicKeyPEM = publicKeyStr
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    public Map<String, Claim> validToken(String jwtToken) {
        var jwt = jwtVerifier.verify(jwtToken);
        return jwt.getClaims();
    }
}
