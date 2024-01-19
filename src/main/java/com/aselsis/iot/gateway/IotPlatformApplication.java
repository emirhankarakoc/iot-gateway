package com.aselsis.iot.gateway;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties
public class IotPlatformApplication {

	public static void main(String[] args) throws Throwable  {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+03:00"));
		Locale.setDefault(Locale.ENGLISH);

		SpringApplication.run(IotPlatformApplication.class, args);
	}
	@Bean
	public ModelMapper getModelMapper(){
		return  new ModelMapper();
	}
}
