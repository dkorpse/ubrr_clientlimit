package ru.ubrr.clientLimit;

import ru.ubrr.util.PropertiesLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"ru.ubrr", "iru.ubrr.kafka"})
public class ClientLimitApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ClientLimitApplication.class);
        springApplication.addListeners(new PropertiesLogger());
        springApplication.run(args);
    }
}
