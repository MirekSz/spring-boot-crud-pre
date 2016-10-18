package hello;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
		model.addAttribute("demo", new Date());
		return "customer/customer-list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		Customer customer = new Customer();
		customer.setFirstName("f");
		model.addAttribute("customer", customer);
		return "customer/customer-form";
	}

	@Autowired
	Validator validator;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute Customer customer,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		handle(customer, bindingResult);
		if (bindingResult.hasErrors()) {
			return "customer/customer-form";
		}

		customer.setId(customers.size() + 1L);
		customers.put(customer.getId(), customer);
		redirectAttributes.addFlashAttribute("added", true);
		return "redirect:/customers";
	}

	public void handle(Customer customer, BindingResult bindingResult) {
		Set<ConstraintViolation<Customer>> validate = validator
				.validate(customer);

		if (validate.size() > 0) {
			for (ConstraintViolation<Customer> constraintViolation : validate) {
				bindingResult.rejectValue(constraintViolation.getPropertyPath()
						.toString(), "NotNull");

			}
		}

	}
}
