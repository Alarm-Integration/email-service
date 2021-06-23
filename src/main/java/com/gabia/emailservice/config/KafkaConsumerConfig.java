package com.gabia.emailservice.config;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap.servers}")
    private String kafkaServer;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SendEmailRequest> emailListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SendEmailRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(emailConsumer());
        return factory;
    }

    @Bean
    public StringJsonMessageConverter jsonMessageConverter() {
        return new StringJsonMessageConverter();
    }

    private ConsumerFactory<String, SendEmailRequest> emailConsumer() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(SendEmailRequest.class));
    }

}
