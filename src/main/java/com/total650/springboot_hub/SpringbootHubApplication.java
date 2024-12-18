package com.total650.springboot_hub;

import com.total650.springboot_hub.utils.ConfigUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootHubApplication {

	public static void main(String[] args) {
		ConfigUtils.loadEnv();
		SpringApplication.run(SpringbootHubApplication.class, args);
	}

}
