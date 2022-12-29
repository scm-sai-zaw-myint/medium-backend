package scm.api.restapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "file")
public class FileConfiguration {

    private String uploadDIR;
    
}
