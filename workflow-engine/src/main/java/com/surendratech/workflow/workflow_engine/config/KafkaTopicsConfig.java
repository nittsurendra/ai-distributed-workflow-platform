package com.surendratech.workflow.workflow_engine.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicsConfig {
    
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic taskEventsTopic() {
        return new NewTopic("workflow-task-events", 3, (short) 1)
            .configs(Map.of("retention.ms", "86400000")); // 24 hours
    }
    
    @Bean
    public NewTopic taskResultsTopic() {
        return new NewTopic("workflow-task-results", 3, (short) 1)
            .configs(Map.of("retention.ms", "86400000")); // 24 hours
    }
}
