package backend.configuration;

import backend.configuration.domain.Settings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class SettingsConfiguration {

    @Value("${settings.configFile}")
    private String settingsConfigFile;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean(name="settingsConfig")
    public Settings settingsConfig() {
        try {
            return objectMapper.readValue(new File(settingsConfigFile), Settings.class);
        } catch (IOException e) {
            return null;
        }
    }
}
