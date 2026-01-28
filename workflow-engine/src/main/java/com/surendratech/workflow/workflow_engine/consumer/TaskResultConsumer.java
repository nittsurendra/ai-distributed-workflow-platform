package com.surendratech.workflow.workflow_engine.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surendratech.workflow.workflow_engine.model.TaskResult;
import com.surendratech.workflow.workflow_engine.service.ExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskResultConsumer {

    private final ObjectMapper objectMapper;
    private final ExecutionService executionService;

    @KafkaListener(
        topics = "workflow-task-results",
        groupId = "workflow-engine"
    )
    public void consume(String message) throws Exception {

        TaskResult result = objectMapper.readValue(message, TaskResult.class);

        log.info(
            "Received TaskResult: task={}, status={}, execution={}",
            result.getTaskId(),
            result.getStatus(),
            result.getExecutionId()
        );

        executionService.handleTaskResult(result);
    }
}
