package kr.go.hrfco.common.util;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class EnCryptor {
    private static final String PASSWORD = "TEST_KEY";
    private static final String SALT = "12345678";

    private static final TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);

    public static String encrypt(String plainText) {
        return encryptor.encrypt(plainText);
    }

    public static String decrypt(String cipherText) {
        return encryptor.decrypt(cipherText);
    }
    
    public static void main(String[] args) {
    	String encryptedDriverClassName = encryptor.encrypt("org.postgresql.Driver");
        String encryptedURL = encryptor.encrypt("jdbc:postgresql://211.209.185.143:5432/flooddb?useUnicode=true&characterEncoding=utf-8");
        String encryptedID = encryptor.encrypt("flooddb_dev");
        String encryptedPWD = encryptor.encrypt("z&5YiUh!Qv");
        
        // System.out.println("DB : " + encryptedDriverClassName+ " | Encrypted URL : " + encryptedURL + " | Encrypted ID : " + encryptedID + " | Encrypted PWD : " + encryptedPWD );
	}
}
