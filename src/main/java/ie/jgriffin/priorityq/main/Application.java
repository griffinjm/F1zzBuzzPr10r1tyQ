package ie.jgriffin.priorityq.main;


import ie.jgriffin.priorityq.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by jgriffin on 17/04/2017.
 */
@SpringBootApplication(scanBasePackageClasses = ApplicationConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
