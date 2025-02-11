package ua.andersen.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BookMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookMsApplication.class, args);
    }
}
