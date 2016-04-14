package pl.com.stream.threadanalyzer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Sending {
	@Autowired
	private SimpMessagingTemplate template;

	@PostConstruct
	@Scheduled(cron = "*/5 * * * * *")
	public void execute() throws Exception {
		List<String> a = new ArrayList<String>();
		a.add("mirek");
		template.convertAndSend("/topic/active", a);
		template.convertAndSendToUser("mirek", "/queue/messages", "siema mirek");
	}
}
