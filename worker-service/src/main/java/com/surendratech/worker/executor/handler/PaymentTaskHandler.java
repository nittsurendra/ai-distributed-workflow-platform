package com.surendratech.worker.executor.handler;

import org.springframework.stereotype.Component;

import com.surendratech.worker.model.TaskEvent;

@Component
public class PaymentTaskHandler implements TaskHandler {

    @Override
    public String getType() {
        return "PAYMENT";
    }

    @Override
    public void handle(TaskEvent event) {
        System.out.println("Processing payment: " + event.getInputs());
    }
}
