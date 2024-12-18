package com.total650.springboot_hub;

import com.total650.springboot_hub.utils.ConfigUtils;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@ComponentScan class will scan every @Bean method in this class or package/sub package
@SpringBootApplication // This annotation = @Configuration + @ComponentScan + @EnableAutoConfiguration
public class SpringbootHubApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
		//Bean method = signing up ModelMapper to be used by @Autowired class as a parameter
	}

	public static void main(String[] args) {
		ConfigUtils.loadEnv();
		SpringApplication.run(SpringbootHubApplication.class, args);
	}

}
