package com.jphf.cloud.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.jphf.cloud.shared.UserMessage;

@Configuration
public class KafkaConsumerConfig {
	
	
	
//	@Bean
//	public ConsumerFactory<String, UserMessage> userMessageConsumerFactory() {
//		Map<String, Object> props = new HashMap<String, Object>();
//		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		props.put(ConsumerConfig.GROUP_ID_CONFIG, "message-group");
//		
//		return new DefaultKafkaConsumerFactory<String, UserMessage>(props, new StringDeserializer(),
//				new JsonDeserializer<UserMessage>(UserMessage.class));
//	}
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, UserMessage> userMessageKafkaListenerContainerFactory() {
//
//		ConcurrentKafkaListenerContainerFactory<String, UserMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(userMessageConsumerFactory());
//		factory.setMessageConverter(new StringJsonMessageConverter());
//		return factory;
//	}
}
