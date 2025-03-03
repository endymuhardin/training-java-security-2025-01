package com.muhardin.endy.training.security.keypair;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KeyPairGeneratorExample {

    public static void main(String[] args) {
        try {
            // Define file paths
            String publicKeyFilePath = "target/public-key.txt";
            String privateKeyFilePath = "target/private-key.txt";

            // Create a KeyPairGenerator object for the RSA algorithm
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            // Initialize the KeyPairGenerator with a key size of 2048 bits
            keyPairGen.initialize(2048);

            // Generate the KeyPair
            KeyPair keyPair = keyPairGen.generateKeyPair();

            // Get the public and private keys from the KeyPair
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Encode the keys in Base64
            String publicKeyEncoded = java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyEncoded = java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded());

            // Print the keys in Base64 encoding
            System.out.println("Public Key: " + publicKeyEncoded);
            System.out.println("Private Key: " + privateKeyEncoded);

            // Write the keys to files
            writeToFile(publicKeyFilePath, publicKeyEncoded);
            writeToFile(privateKeyFilePath, privateKeyEncoded);

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(String path, String key) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(key);
        }
    }
}