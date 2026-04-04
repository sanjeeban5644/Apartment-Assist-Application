package com.sanjeeban.CoreApartmentService.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.pdftopic}")
    private String KAFKA_PDF_TOPIC;

    @Bean
    public NewTopic userResidentTopic(){
        return new NewTopic(KAFKA_PDF_TOPIC,1,(short)1);
    }

}
