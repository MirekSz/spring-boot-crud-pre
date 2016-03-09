package pl.com.stream.threadanalyzer;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@HystrixCommand(groupKey = "XmlTransactionThingsDao", commandKey = "retrieve", fallbackMethod = "invokeRemoteService", commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2"))
	@RequestMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		long sum = 0;
		for (long i = 0; i < 10000000; i++) {
			sum += i;
		}
		System.out.println(sum);
		invokeRemoteService(sum + "");
		return new Greeting(counter.incrementAndGet(), String.format(template,
				name));

	}

	public Greeting invokeRemoteService(String input) {
		return new Greeting(counter.incrementAndGet(), String.format(template,
				"yo"));
	}

	public String defaultInvokeRemoteService(String input) {
		return input;
	}
}
