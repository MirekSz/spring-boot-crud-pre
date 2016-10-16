package hello;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
public class CustomersController {
	Map<Long, Customer> customers = new HashMap<Long, Customer>();

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@PostConstruct
	public void init() {
		customers.put(1L, new Customer(1L, "mire", "sz", 30));
		customers.put(2L, new Customer(2L, "mire", "sz", 30));
		customers.put(3L, new Customer(31L, "mire", "sz", 30));
	}

	@RequestMapping
	public String list(Model model) {
		model.addAttribute("customers", customers.values());
		return "customer/customer-list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer/customer-form";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute @Valid Customer customer, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "customer/customer-form";
		}
		customer.setId(customers.size() + 1L);
		customers.put(customer.getId(), customer);
		redirectAttributes.addFlashAttribute("added", true);
		return "redirect:/customers";
	}
}
