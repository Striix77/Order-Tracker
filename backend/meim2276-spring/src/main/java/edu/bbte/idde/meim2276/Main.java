package edu.bbte.idde.meim2276;

import edu.bbte.idde.meim2276.backend.config.VapidProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "edu.bbte.idde.meim2276")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}