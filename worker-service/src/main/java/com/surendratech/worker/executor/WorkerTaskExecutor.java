package com.surendratech.worker.executor;

import com.surendratech.worker.executor.handler.TaskHandler;
import com.surendratech.worker.model.TaskEvent;
import com.surendratech.worker.producer.TaskResultProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkerTaskExecutor {

    private final Map<String, TaskHandler> handlers;
    private final TaskResultProducer producer;

    public WorkerTaskExecutor(List<TaskHandler> handlerList,
                              TaskResultProducer producer) {
        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(TaskHandler::getType, h -> h));
        this.producer = producer;
    }

    public void execute(TaskEvent event) {
        TaskHandler handler = handlers.get(event.getType());

        if (handler == null) {
            producer.sendFailure(event, "No handler for task type");
            return;
        }

        try {
            handler.handle(event);
            producer.sendSuccess(event);
        } catch (Exception ex) {
            producer.sendFailure(event, ex.getMessage());
        }
    }
}
