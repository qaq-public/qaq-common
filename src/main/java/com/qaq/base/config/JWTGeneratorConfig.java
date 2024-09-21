package com.qaq.base.config;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qaq.base.component.JWTGeneratorComponent;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rsa")
public class JWTGeneratorConfig {

    @Setter
    @Getter
    private String privateKey;

    @Setter
    @Getter
    private Integer tokenExpTimeInDay = 10;

    // https://stackoverflow.com/questions/39311157/only-rsaprivate-crt-keyspec-and-pkcs8encodedkeyspec-supported-for-rsa-private
    @Bean
    public JWTGeneratorComponent jwtGeneratorComponent() {
        Security.addProvider(new BouncyCastleProvider());
        try ( PemReader pemReader = new PemReader(new StringReader(privateKey))){
            byte[] keyBytes = pemReader.readPemObject().getContent();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return new JWTGeneratorComponent((RSAPrivateKey) kf.generatePrivate(spec), tokenExpTimeInDay);
        } catch (IOException e) {
            log.error("read private key file failed, privateKeyStr: {}", privateKey);
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            log.error("can not create the specified KeyFactory, check the algorithm !!!");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            log.error("the key is not valid, key file path: {}", privateKey);
        }
        //不应该走到这里
        System.exit(-1);
        return null;
        
    }
}
