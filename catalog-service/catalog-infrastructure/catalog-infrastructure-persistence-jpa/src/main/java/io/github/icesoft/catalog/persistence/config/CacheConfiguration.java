package io.github.icesoft.catalog.persistence.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class CacheConfiguration {

	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory factory, ObjectMapper objectMapper) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJacksonJsonRedisSerializer(objectMapper)));

		return RedisCacheManager.builder(factory).cacheDefaults(config).build();
	}

	@Bean
	public CaffeineCacheManager caffeineCacheManager() {
		CaffeineCacheManager manager = new CaffeineCacheManager();
		manager.setCaffeine(Caffeine.newBuilder().maximumSize(10_000).expireAfterWrite(5, TimeUnit.MINUTES));
		return manager;
	}

	@Bean
	@Primary
	public CacheManager cacheManager(CaffeineCacheManager caffeine, RedisCacheManager redis) {

		CompositeCacheManager manager = new CompositeCacheManager(caffeine, redis);
		manager.setFallbackToNoOpCache(false);
		return manager;
	}

}
