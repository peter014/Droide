package app.configurations;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "app.controllers","app.dto","app.interfaces","app.services","app.configurations" })
public class MainApp {

	public static void main(String[] args) {
		 SpringApplication.run(MainApp.class, args);
	}

}
