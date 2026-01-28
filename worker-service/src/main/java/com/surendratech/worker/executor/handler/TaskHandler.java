package com.surendratech.worker.executor.handler;

import com.surendratech.worker.model.TaskEvent;

public interface TaskHandler {
    String getType();
    void handle(TaskEvent event) throws Exception;
}
