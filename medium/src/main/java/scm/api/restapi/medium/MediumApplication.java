package scm.api.restapi.medium;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import scm.api.restapi.config.FileConfiguration;

@EnableWebMvc
@ServletComponentScan
@SpringBootApplication(scanBasePackages = "scm.api.restapi")
@EnableConfigurationProperties({
    FileConfiguration.class
})
public class MediumApplication {

	public static void main(String[] args) {
	    SpringApplicationBuilder builder = new SpringApplicationBuilder(MediumApplication.class);
        builder.headless(false).run(args);
	}

}
