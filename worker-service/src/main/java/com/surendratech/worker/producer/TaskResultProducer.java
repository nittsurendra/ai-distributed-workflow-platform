package com.surendratech.worker.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surendratech.worker.model.TaskEvent;
import com.surendratech.worker.model.TaskResult;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TaskResultProducer {

    private static final String TASK_RESULTS_TOPIC = "workflow-task-results";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public TaskResultProducer(KafkaTemplate<String, String> kafkaTemplate,
                              ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public void sendSuccess(TaskEvent event) {
        send(new TaskResult(
                event.getEventId(),
                event.getExecutionId(),
                event.getTaskId(),
                "SUCCESS",
                null,
                Instant.now()
        ));
    }

    public void sendFailure(TaskEvent event, String error) {
        send(new TaskResult(
                event.getEventId(),
                event.getExecutionId(),
                event.getTaskId(),
                "FAILED",
                error,
                Instant.now()
        ));
    }

    private void send(TaskResult result) {
        try {
            kafkaTemplate.send(
                    TASK_RESULTS_TOPIC,
                    result.getEventId(),
                    mapper.writeValueAsString(result)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to send TaskResult", e);
        }
    }
}
