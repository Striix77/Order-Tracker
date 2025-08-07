package edu.bbte.idde.meim2276.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.io.IOException;

public class ConfigLoader {
    private static volatile AppConfig appConfig;
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ConfigLoader.class);

    public static synchronized AppConfig loadConfig() {
        if (appConfig == null) {
            appConfig = new AppConfig();
            String profile = getProfile();
            String configFile = "config-" + profile + ".json";
            try {
                ObjectMapper mapper = new ObjectMapper();
                appConfig = mapper.readValue(Thread.currentThread().getContextClassLoader().getResource(configFile),
                        AppConfig.class);
            } catch (IOException e) {
                LOG.error("Error loading configuration file: {}", configFile, e);
            }
        }
        return appConfig;
    }

    private static String getProfile() {
        String profile = System.getenv("IMPL_TYPE");
        if (profile == null || profile.isEmpty()) {
            profile = "InMem";
        }
        return profile;
    }
}