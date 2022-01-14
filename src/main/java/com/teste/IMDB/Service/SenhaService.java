package com.teste.IMDB.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class SenhaService {

    public String criptografaSenha(String original) {
        String senha = "";
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = algorithm.digest(original.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) hexString.append(String.format("%02X", 0xFF & b));
            senha = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return senha;
    }

    public String decodeSenha(String senha) {
        return new String(Base64.getDecoder().decode(senha));
    }

}
