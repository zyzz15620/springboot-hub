package com.total650.springboot_hub;

import com.total650.springboot_hub.entity.Category;
import com.total650.springboot_hub.entity.Role;
import com.total650.springboot_hub.repository.CategoryRepository;
import com.total650.springboot_hub.repository.RoleRepository;
import com.total650.springboot_hub.utils.ConfigEnv;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.Inherited;

//@ComponentScan class will scan every @Bean method in this class or package/sub package
@SpringBootApplication // This annotation = @Configuration + @ComponentScan + @EnableAutoConfiguration
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog REST APIs",
				description = "Spring Boot Blog REST APIs Documentation",
				contact = @Contact(
						name = "Total650",
						email = "duc.total650@gmail.com",
						url = "https://www.linkedin.com/in/pham-anh-duc-a65544179/"
				),
				license =@License(
						name = ""
				)
		)
)
public class SpringbootHubApplication  implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
		//Bean method = signing up ModelMapper to be used by @Autowired class as a parameter
	}

	public static void main(String[] args) {
		ConfigEnv.loadEnv();
		SpringApplication.run(SpringbootHubApplication.class, args);
	}


	//Insert Metadata to Table
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);
		Role userRole = new Role();
		userRole.setName("USER_ADMIN");
		roleRepository.save(userRole);

		Category java = new Category();
		java.setName("Java");
		java.setDescription("Programming Language");
		categoryRepository.save(java);
		Category selenium = new Category();
		selenium.setName("Selenium");
		selenium.setDescription("Web Automation Testing Tool");
		categoryRepository.save(selenium);
		Category playwright = new Category();
		playwright.setName("PlayWright");
		playwright.setDescription("Web Automation Testing Tool");
		categoryRepository.save(playwright);
		Category appium = new Category();
		appium.setName("Appium");
		appium.setDescription("Mobile Automation Testing Tool");
		categoryRepository.save(appium);
		Category restAssured = new Category();
		restAssured.setName("RestAssured");
		restAssured.setDescription("API Automation Testing Tool");
		categoryRepository.save(restAssured);
		Category manual = new Category();
		manual.setName("Manual");
		manual.setDescription("Everything about Manual Testing");
		categoryRepository.save(manual);
		Category health = new Category();
		health.setName("Health");
		health.setDescription("Topic outside Testing");
		categoryRepository.save(health);
	}
}
