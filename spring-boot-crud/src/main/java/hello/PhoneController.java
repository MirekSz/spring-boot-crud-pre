package hello;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PhoneController {
	@Value("classpath:/public/phones")
	Resource resource;
	List<String> images = new ArrayList<String>();

	@PostConstruct
	public void init() throws Exception {
		File file = resource.getFile();
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			images.add(file2.getName());
		}
		System.out.println(images);
	}

	Random r = new Random();

	@RequestMapping("/getRandomLinks")
	public String getRandomLinks(Model model) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			int next = r.nextInt(images.size()) + 1;
			result.add(images.get(next));
		}
		model.addAttribute("images", result);
		return "images";
	}
}
