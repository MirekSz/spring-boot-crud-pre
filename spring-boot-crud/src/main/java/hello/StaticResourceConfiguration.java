package hello;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(
				CLASSPATH_RESOURCE_LOCATIONS);
	}

	@Override
	public void addFormatters(FormatterRegistry formatterRegistry) {
		formatterRegistry.addConverter(new Converter<Date, String>() {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd MMMM yyyy HH:mm:ss");

			@Override
			public String convert(Date arg0) {
				return simpleDateFormat.format(arg0);
			}
		});
	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}
}
