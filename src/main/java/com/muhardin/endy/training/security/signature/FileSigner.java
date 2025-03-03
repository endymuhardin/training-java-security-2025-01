package com.muhardin.endy.training.security.signature;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class FileSigner {
    public static void main(String[] args) throws Exception {
        // Define file paths
        String privateKeyFilePath = "target/private-key.txt";
        String inputFilePath = "README.md";
        String signatureFilePath = "target/README.md.sig";

        // Read the private key from file
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(privateKeyFilePath));
        byte[] decodedPrivateKeyBytes = Base64.getDecoder().decode(privateKeyBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedPrivateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(spec);

        // Read the file to sign
        byte[] fileBytes = Files.readAllBytes(Paths.get(inputFilePath));

        // Sign the file
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(fileBytes);
        byte[] digitalSignature = signature.sign();

        // Base64 encode the signature
        byte[] base64EncodedSignature = Base64.getEncoder().encode(digitalSignature);

        // Save the base64 encoded signature
        Files.write(Paths.get(signatureFilePath), base64EncodedSignature);
    }
}