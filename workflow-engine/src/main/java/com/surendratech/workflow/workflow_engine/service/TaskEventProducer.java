package com.surendratech.workflow.workflow_engine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surendratech.workflow.workflow_engine.model.TaskEvent;
import com.surendratech.workflow.workflow_engine.model.TaskResult;

@Service
public class TaskEventProducer {

    private static final Logger log = LoggerFactory.getLogger(TaskEventProducer.class);

    private static final String TASK_EVENTS_TOPIC = "workflow-task-events";
    private static final String TASK_RESULTS_TOPIC = "workflow-task-results";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public TaskEventProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Emit task event to Kafka for workers to consume
     */
    public void publishTaskEvent(TaskEvent event) {
    try {
        String payload = objectMapper.writeValueAsString(event);
        kafkaTemplate.send(TASK_EVENTS_TOPIC, event.getEventId(), payload);
    } catch (JsonProcessingException | KafkaException e) {
        throw new RuntimeException("Failed to publish task event", e);
    }
}

    /**
     * Emit task result (future use)
     */
    public void publishTaskResult(TaskResult result) {
        try {
            String payload = objectMapper.writeValueAsString(result);
            log.info(
                "Publishing task result: {} with status: {}",
                result.getTaskId(),
                result.getStatus()
            );
            kafkaTemplate.send(TASK_RESULTS_TOPIC, result.getEventId(), payload);
        } catch (JsonProcessingException | KafkaException e) {
            throw new RuntimeException("Failed to publish task result", e);
        }
    }
}
