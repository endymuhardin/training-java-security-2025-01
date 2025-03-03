package com.muhardin.endy.training.security.signature;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class FileVerifier {
    public static void main(String[] args) throws Exception {
        // Define file paths
        String publicKeyFilePath = "target/public-key.txt";
        String inputFilePath = "README.md";
        String signatureFilePath = "target/README.md.sig";

        // Read the public key from file
        byte[] publicKeyBytes = Files.readAllBytes(Paths.get(publicKeyFilePath));
        byte[] decodedPublicKeyBytes = Base64.getDecoder().decode(publicKeyBytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedPublicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);

        // Read the file to verify
        byte[] fileBytes = Files.readAllBytes(Paths.get(inputFilePath));

        // Read the signature from file
        byte[] signatureBytes = Files.readAllBytes(Paths.get(signatureFilePath));
        byte[] decodedSignatureBytes = Base64.getDecoder().decode(signatureBytes);

        // Verify the signature
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(fileBytes);
        boolean isVerified = signature.verify(decodedSignatureBytes);

        // Print the verification result
        System.out.println("Signature verification result: " + isVerified);
    }
}