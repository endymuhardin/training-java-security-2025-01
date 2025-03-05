package com.muhardin.endy.training.security.keypair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.math.BigInteger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PublicKeyGenerator {
    public static void main(String[] args) throws Exception {
        // Define file paths
        String privateKeyFilePath = "src/main/resources/private-key.txt";
        String publicKeyFilePath = "target/public-key.txt";

        // Read the private key from file
        String privateKeyContent = new String(Files.readAllBytes(Paths.get(privateKeyFilePath)));
        privateKeyContent = privateKeyContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decodedPrivateKeyBytes = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodedPrivateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        // Generate the public key from the private key
        RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaPrivateKeySpec.getModulus(), BigInteger.valueOf(65537));
        PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);

        // Encode the public key in Base64
        String publicKeyEncoded = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        // Save the public key to file
        writeToFile(publicKeyFilePath, publicKeyEncoded);

        // Display the modulus
        BigInteger modulus = rsaPublicKeySpec.getModulus();
        System.out.println("Modulus: " + modulus);

        // Display the signature based on the modulus
        String modulusSignature = Base64.getEncoder().encodeToString(modulus.toByteArray());
        System.out.println("Signature based on modulus: " + modulusSignature);
    }

    private static void writeToFile(String path, String key) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(key);
        }
    }
}