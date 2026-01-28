package com.surendratech.worker.executor.handler;

import org.springframework.stereotype.Component;

import com.surendratech.worker.model.TaskEvent;

@Component
public class OrderValidationHandler implements TaskHandler {

    @Override
    public String getType() {
        return "ORDER_VALIDATION";
    }

    @Override
    public void handle(TaskEvent event) {
        System.out.println("Validating order: " + event.getInputs());
    }
}
