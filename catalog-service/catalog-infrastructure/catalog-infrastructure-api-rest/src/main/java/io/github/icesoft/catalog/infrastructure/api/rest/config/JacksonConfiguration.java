package io.github.icesoft.catalog.infrastructure.api.rest.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.module.blackbird.BlackbirdModule;

@Configuration
@ConditionalOnProperty(name = "app.jackson.blackbird.enabled", havingValue = "true")
public class JacksonConfiguration {

	@Bean
	JsonMapperBuilderCustomizer jsonCustomizer() {
		return builder -> builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
				.disable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT).addModule(new BlackbirdModule());
	}

}