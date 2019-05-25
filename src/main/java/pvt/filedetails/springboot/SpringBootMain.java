package pvt.filedetails.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMain {
	private static final Logger logger = LoggerFactory.getLogger(SpringBootMain.class);

	public static void startSpringBootRest(String[] args) {
		logger.info("this is a info message");
		logger.warn("this is a warn message");
		logger.error("this is a error message");
		SpringApplication.run(SpringBootMain.class, args);
	}

}
