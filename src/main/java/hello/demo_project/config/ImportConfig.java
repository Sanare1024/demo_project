package hello.demo_project.config;

import com.siot.IamportRestClient.IamportClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
public class ImportConfig {

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;

    @Bean
    public IamportClient init() {
        return new IamportClient(apiKey, secretKey);
    }
}
