package kr.go.hrfco.common.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class DeCryptor extends PropertyPlaceholderConfigurer {
	private static final String ENC_PREFIX = "ENC(";
    private static final String ENC_SUFFIX = ")";

    private static final TextEncryptor encryptor = Encryptors.text("TEST_KEY", "12345678");

    private static final Logger log = LoggerFactory.getLogger(DeCryptor.class);
    
    @Override
    protected void convertProperties(Properties props) {
        for(String key : props.stringPropertyNames()) {
            String value = props.getProperty(key);
            if(value != null && value.startsWith(ENC_PREFIX) && value.endsWith(ENC_SUFFIX)) {
                String encrypted = value.substring(4, value.length() - 1);
                try {
                    String decrypted = encryptor.decrypt(encrypted);
                    props.setProperty(key, decrypted);
                    
                } catch (IllegalArgumentException ie) {
                    // throw new IllegalArgumentException("dec fail: " + key, e);
                	log.error("dec fail: " + key, ie);
                }
            }
        }
        super.convertProperties(props);
    }
	
}
