package com.example.fleetManagementWebApp.securityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile("production")
public class SecurityConfiguration {

	//Authentication
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("rac") //user name
		.password("{noop}secret") //temp password
		.roles("USER","ADMIN"); //roles user has									
	}	

	//Authorization
	@Configuration
	@Order(2)                                                        
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
			.antMatcher("/**")                               
			.authorizeRequests()
			.anyRequest().hasRole("ADMIN")
			.and()
			.httpBasic()
			.and()			 
			.csrf() //cross site request forgeries			
			.disable()
			.headers()
			.frameOptions()
			.disable();
			
		}
	}

	@Configuration 
	@Order(1)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {

			http.antMatcher("/website/**").authorizeRequests()
			.anyRequest().hasRole("USER") //for any request, user must have a role
			.and()
			.formLogin().loginPage("/website/login.jsp")
			.failureUrl("/website/login.jsp?error=1")
			.loginProcessingUrl("/website/login")
			.permitAll()
			.and()
			.logout()
			.logoutSuccessUrl("/website/vehicles/list.html"); //when logout, redirect to this page


		}
	}

}
