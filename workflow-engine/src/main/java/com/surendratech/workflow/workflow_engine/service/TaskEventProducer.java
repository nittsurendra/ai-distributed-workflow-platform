package com.surendratech.workflow.workflow_engine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.surendratech.workflow.workflow_engine.model.TaskEvent;
import com.surendratech.workflow.workflow_engine.model.TaskResult;

@Service
public class TaskEventProducer {
    private static final Logger log = LoggerFactory.getLogger(TaskEventProducer.class);
    
    private static final String TASK_EVENTS_TOPIC = "workflow-task-events";
    private static final String TASK_RESULTS_TOPIC = "workflow-task-results";
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public TaskEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    /**
     * Emit task event to Kafka for workers to consume
     */
    public void publishTaskEvent(TaskEvent event) {
        log.info("Publishing task event: {} for execution: {}", event.getTaskId(), event.getExecutionId());
        kafkaTemplate.send(TASK_EVENTS_TOPIC, event.getEventId(), event);
    }
    
    /**
     * Consume task results from workers (will be implemented in Phase 4)
     */
    public void publishTaskResult(TaskResult result) {
        log.info("Publishing task result: {} with status: {}", result.getTaskId(), result.getStatus());
        kafkaTemplate.send(TASK_RESULTS_TOPIC, result.getEventId(), result);
    }
}
