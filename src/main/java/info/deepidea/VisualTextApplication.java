package info.deepidea;

import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Configuration
public class VisualTextApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisualTextApplication.class, args);
	}

	@Bean
	public OkHttpClient httpClinet() {
		return new OkHttpClient();
	}

	@Bean
	public ExecutorService threadPool(){
		return Executors.newWorkStealingPool();
	}
}
