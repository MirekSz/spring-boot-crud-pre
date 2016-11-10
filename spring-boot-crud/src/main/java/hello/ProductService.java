package hello;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	AutowireCapableBeanFactory beanFactory;

	@Autowired
	private ObjectFactory<Operation> prototypeFactory;

	@Autowired
	public void list() {
		System.out.println(prototypeFactory.getObject());
		System.out.println(prototypeFactory.getObject().ps);

		Operation operation = new Operation();
		beanFactory.autowireBean(operation);
		System.out.println(operation);
		System.out.println(operation.ps);
	}
}
