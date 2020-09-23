package com.onlinetest.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.onlinetest.backend.dto.User;
import com.onlinetest.backend.service.IJwtService;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
    private IJwtService jwtService;
	
	@Bean
	public Docket postsApi() {
		List<Parameter> global = new ArrayList<>();
		User userInfo = new User(2, "test", null, 1, "test", "test@test.com");
		global.add(new ParameterBuilder().name("access-token")
								  .description("Access Token (기본설정 - id:2, user_id:test, auth:1)")
								  .parameterType("header")
								  .defaultValue(jwtService.create("user", userInfo, "userInfo"))
								  .required(false).modelRef(new ModelRef("string")).build());
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("public-api")
				.globalOperationParameters(global)
				.apiInfo(apiInfo()).select().paths(regex("/api.*"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Online-Test API")
				.description("Online-Test Swagger")
				.version("1.0").build();
	}

}

//http://localhost:8080/swagger-ui.html#/