package com.surendratech.worker.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surendratech.worker.executor.WorkerTaskExecutor;
import com.surendratech.worker.model.TaskEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventConsumer {

    private final ObjectMapper mapper;
    private final WorkerTaskExecutor executor;

    public TaskEventConsumer(ObjectMapper mapper, WorkerTaskExecutor executor) {
        this.mapper = mapper;
        this.executor = executor;
    }

    @KafkaListener(topics = "workflow-task-events", groupId = "workflow-workers")
    public void consume(String message) throws Exception {
        TaskEvent event = mapper.readValue(message, TaskEvent.class);
        executor.execute(event);
    }
}
