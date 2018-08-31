package top.bestplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmashrabbitApplication{


   /* @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(SmashrabbitApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(SmashrabbitApplication.class, args);
    }
}
