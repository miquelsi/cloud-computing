package net.miquelsi.springbootconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RefreshScope // Refresh dependencies when calling POST spring-boot-config:9080/actuator/refresh (this microservice)
public class GreetingController {

    @Value("${my.greeting: default value}")
    private String greetingMessage;

    @Value("${my.list.values}")
    private List<String> listValues;

    @Value("#{${dbValues}}") // # treats the content as a spring expression
    private Map<String,String> dbValues;

    @Autowired
    private DbSettings dbSettings;

    @Autowired
    private Environment environment; // The use of Environment is discouraged. Use @Value and ${} instead

    @GetMapping("/greeting")
    public String getGreeting() {
        return greetingMessage + dbSettings.getConnection();
    }

    @GetMapping("/envdetails")
    public String envDetails() {
        return environment.toString();
    }
}
