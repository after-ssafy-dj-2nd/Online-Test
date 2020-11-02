package com.onlinetest.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
public class SwaggerConfig extends WebMvcConfigurationSupport{

	@Autowired
    private IJwtService jwtService;
	
	@Bean
	public Docket postsApi() {
		List<Parameter> global = new ArrayList<>();
		User userInfo = new User(2, "teacher@test.com", "teacher", 1, "teacherTest");

		global.add(new ParameterBuilder().name("access-token")
								  .description("Access Token (기본설정 - id:2, email:teacher@test.com, auth:1)")
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

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
		registry.addRedirectViewController("/api/swagger-resources/configuration/ui",
				"/swagger-resources/configuration/ui");
		registry.addRedirectViewController("/api/swagger-resources/configuration/security",
				"/swagger-resources/configuration/security");
		registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/api/**").addResourceLocations("classpath:/META-INF/resources/");
	}
}

//http://localhost:8080/swagger-ui.html#/