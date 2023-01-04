package scm.api.restapi.medium;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ServletComponentScan
@SpringBootApplication(scanBasePackages = "scm.api.restapi")
public class MediumApplication {

	public static void main(String[] args) {
	    SpringApplicationBuilder builder = new SpringApplicationBuilder(MediumApplication.class);
        builder.headless(false).run(args);
	}

}
