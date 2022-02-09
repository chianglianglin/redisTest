package com.ws.config;

import com.google.common.base.Predicates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket webApiConfig() {

		return new Docket(DocumentationType.SWAGGER_2)
				// 详细定制
				.apiInfo(webApiInfo())
				.select()
				// 指定当前包路径
				.apis(RequestHandlerSelectors.basePackage("com.ws"))
				// 扫描所有 .apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();

	}

	private ApiInfo webApiInfo() {

		return new ApiInfoBuilder()
				.title("cache資訊")
				.description("cache")
				.version("1.0")
				.build();
	}

}
