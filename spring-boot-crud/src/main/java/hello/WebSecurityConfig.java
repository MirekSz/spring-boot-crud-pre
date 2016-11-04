package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	// super.configure(http);
	// }

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.userDetailsService(new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
				return new SystemUser();
			}
		});
		// auth.inMemoryAuthentication().withUser("mirek").password("mirek").roles("USER");

	}

	@Bean
	public EvaluationContextExtension securityExtension() {
		return new SecurityEvaluationContextExtensionImpl();
	}

	public static class SecurityEvaluationContextExtensionImpl extends EvaluationContextExtensionSupport {
		@Override
		public String getExtensionId() {
			return "security";
		}

		@Override
		public Object getRootObject() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return new SecurityExpressionRoot(authentication) {
			};
		}
	}
}
