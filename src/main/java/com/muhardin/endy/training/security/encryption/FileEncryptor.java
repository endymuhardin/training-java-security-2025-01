package com.muhardin.endy.training.security.encryption;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class FileEncryptor {
    public static void main(String[] args) throws Exception {
        // Define file paths
        String publicKeyFilePath = "target/public-key.txt";
        String inputFilePath = "README.md";
        String encryptedFilePath = "target/README.md.enc";

        // Read the public key from file
        byte[] publicKeyBytes = Files.readAllBytes(Paths.get(publicKeyFilePath));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);

        // Read the file to encrypt
        byte[] fileBytes = Files.readAllBytes(Paths.get(inputFilePath));

        // Encrypt the file
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(fileBytes);

        // Save the encrypted file
        Files.write(Paths.get(encryptedFilePath), encryptedBytes);
    }
}