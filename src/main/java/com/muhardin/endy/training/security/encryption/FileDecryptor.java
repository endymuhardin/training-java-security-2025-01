package com.muhardin.endy.training.security.encryption;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class FileDecryptor {
    public static void main(String[] args) throws Exception {
        // Define file paths
        String privateKeyFilePath = "target/private-key.txt";
        String encryptedFilePath = "target/contoh.txt.enc";
        String decryptedFilePath = "target/contoh.txt";

        // Read the private key from file
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(privateKeyFilePath));
        byte[] decodedPrivateKeyBytes = Base64.getDecoder().decode(privateKeyBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedPrivateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(spec);

        // Read the encrypted file
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(encryptedFilePath));
        byte[] decodedEncryptedBytes = Base64.getDecoder().decode(encryptedBytes);

        // Decrypt the file
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(decodedEncryptedBytes);

        // Save the decrypted file
        Files.write(Paths.get(decryptedFilePath), decryptedBytes);
    }
}