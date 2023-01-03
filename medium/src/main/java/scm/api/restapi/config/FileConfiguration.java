package scm.api.restapi.config;

import java.util.Base64;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "file")
public class FileConfiguration {

    private String uploadDIR;
    
    public static String nameFile(String name) {
        return Base64.getEncoder().encodeToString(name.getBytes());
    }
    
    public static String getNamedFile(String filename) {
        return new String(Base64.getDecoder().decode(filename));
    }
}
