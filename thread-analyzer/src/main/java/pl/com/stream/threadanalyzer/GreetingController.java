package pl.com.stream.threadanalyzer;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		long sum = 0;
		for (long i = 0; i < 1000000; i++) {
			sum += i;
		}
		System.out.println(sum);
		return new Greeting(counter.incrementAndGet(), String.format(template,
				name));
	}
}
