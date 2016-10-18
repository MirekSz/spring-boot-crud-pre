package hello;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auctions")
public class AuctionsController {
	Map<Long, Auction> auctions = new HashMap<Long, Auction>();

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@PostConstruct
	public void init() {
		auctions.put(1L, new Auction(1L, "Dysk SSD", "Dysk SSD", BigDecimal.TEN));
		auctions.put(2L, new Auction(2L, "DDR 16GB", "DDR 16GB", BigDecimal.TEN));
	}

	@RequestMapping
	public String list(Model model) {
		model.addAttribute("auctions", auctions.values());
		model.addAttribute("demo", new Date());
		return "auction/auction-list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		Auction auction = new Auction();
		model.addAttribute("auction", auction);
		return "auction/auction-form";
	}

	@Autowired
	Validator validator;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute Auction auction, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile uploadfile) throws Exception {
		String originalFilename = uploadfile.getOriginalFilename();
		File file = new File("");
		String absolutePath = file.getAbsolutePath();
		String savePath = absolutePath + File.separator + "src" + File.separator + "main" + File.separator
				+ "resources" + File.separator + "public" + File.separator + "phones";
		FileUtils.copyInputStreamToFile(uploadfile.getInputStream(), new File(savePath + File.separator
				+ originalFilename));
		handle(auction, bindingResult);
		if (bindingResult.hasErrors()) {
			return "auction/auction-form";
		}

		auction.setId(auctions.size() + 1L);
		auctions.put(auction.getId(), auction);
		redirectAttributes.addFlashAttribute("added", true);
		return "redirect:/auctions";
	}

	public void handle(Auction auction, BindingResult bindingResult) {
		Set<ConstraintViolation<Auction>> validate = validator.validate(auction);

		if (validate.size() > 0) {
			for (ConstraintViolation<Auction> constraintViolation : validate) {
				bindingResult.rejectValue(constraintViolation.getPropertyPath().toString(), "NotNull");

			}
		}

	}
}
