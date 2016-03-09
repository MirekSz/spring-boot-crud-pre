package pl.com.stream.threadanalyzer;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.coyote.http11.Http11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
@EnableAsync
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector());
		tomcat.addAdditionalTomcatConnectors(createSslConnector2());
		return tomcat;
	}

	private Connector createSslConnector() {
		Connector connector = new Connector(
				"org.apache.coyote.http11.Http11Protocol");
		Http11Protocol protocol = (Http11Protocol) connector
				.getProtocolHandler();
		try {
			connector.setScheme("http");
			connector.setPort(8070);
			protocol.setKeyAlias("apitester");
			return connector;
		} catch (Exception ex) {
			throw new IllegalStateException("can't access keystore: ["
					+ "keystore" + "] or truststore: [" + "keystore" + "]", ex);
		}
	}

	private Connector createSslConnector2() {
		Connector connector = new Connector(
				"org.apache.coyote.http11.Http11Nio2Protocol");
		Http11Nio2Protocol protocol = (Http11Nio2Protocol) connector
				.getProtocolHandler();
		try {
			connector.setScheme("http");
			connector.setPort(8060);
			protocol.setKeyAlias("apitester");
			return connector;
		} catch (Exception ex) {
			throw new IllegalStateException("can't access keystore: ["
					+ "keystore" + "] or truststore: [" + "keystore" + "]", ex);
		}
	}
}
