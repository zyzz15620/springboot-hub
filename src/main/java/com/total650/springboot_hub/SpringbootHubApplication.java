package com.total650.springboot_hub;

import com.total650.springboot_hub.entity.Category;
import com.total650.springboot_hub.entity.Role;
import com.total650.springboot_hub.repository.AccountRepository;
import com.total650.springboot_hub.repository.CategoryRepository;
import com.total650.springboot_hub.repository.RoleRepository;
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
		SpringApplication.run(SpringbootHubApplication.class, args);
	}


	//Insert Metadata to Table
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	AccountRepository accountRepository;

	@Override
	public void run(String... args) throws Exception {
		addRoleMetadata("ROLE_ADMIN");
		addRoleMetadata("ROLE_USER");

		addCategoryMetadata("Java", "Programming Language");
		addCategoryMetadata("Selenium", "Web Automation Testing Tool");
		addCategoryMetadata("PlayWright", "Web Automation Testing Tool");
		addCategoryMetadata("Appium", "Mobile Automation Testing Tool");
		addCategoryMetadata("RestAssured", "API Automation Testing Tool");
		addCategoryMetadata("Manual", "Everything about Manual Testing");
		addCategoryMetadata("Health", "Topic outside Testing");

	}

	private void addRoleMetadata(String ROLE_ADMIN) {
		if (!roleRepository.existsByName(ROLE_ADMIN)) {
			Role adminRole = new Role();
			adminRole.setName(ROLE_ADMIN);
			roleRepository.save(adminRole);
		}
	}
	private void addCategoryMetadata(String categoryName, String categoryDesc) {
		if (!categoryRepository.existsByName(categoryName)) {
			Category java = new Category();
			java.setName(categoryName);
			java.setDescription(categoryDesc);
			categoryRepository.save(java);
		}
	}
}
