package info.deepidea;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import info.deepidea.service.CacheClient;
import info.deepidea.service.HazelcastCacheClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Configuration
@ConfigurationProperties
public class VisualTextApplication {

	@Value("${HAZELCAST_SERVER:localhost}")
	private String hazelcastServer;

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

	@Bean
	public CacheClient cacheClient(){
		return new HazelcastCacheClient(hazelcastInstance());
	}

	@Bean
	public HazelcastInstance hazelcastInstance(){
		Properties properties = new Properties();
		properties.setProperty("hazelcast.server", hazelcastServer);

		XmlClientConfigBuilder xmlClientConfigBuilder = new XmlClientConfigBuilder();
		xmlClientConfigBuilder.setProperties(properties);
		ClientConfig clientConfig = xmlClientConfigBuilder.build();

		return HazelcastClient.newHazelcastClient(clientConfig);
	}

}
