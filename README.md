# 🚀 AI-Powered Distributed Workflow Platform  

A **cloud-native, distributed workflow orchestration platform** that executes complex business processes across microservices using **Kafka-driven event orchestration** and **AI-powered failure analysis**.

This system is inspired by how platforms like **Zomato, Flipkart, Uber** coordinate orders, payments, inventory, and compute jobs at massive scale.

---

## 🧠 What Problem Does This Solve?

Modern applications are no longer single monoliths — they are **hundreds of microservices**.

A simple action like:

> “Place an order”

requires:
1. Payment service  
2. Inventory service  
3. Order service  
4. Delivery service  
5. Notification service  

If any step fails, engineers today:
- Dig through logs  
- Manually retry  
- Debug for hours  

This platform provides:

> **A single system that executes, tracks, retries, and heals workflows automatically.**

---

## 🧩 What This Platform Does

| Feature | Description |
|-------|-------------|
| **Workflow Engine** | Executes multi-step business processes |
| **Distributed Execution** | Runs tasks across multiple workers |
| **Kafka Event Bus** | Coordinates all services |
| **Persistent State** | Every step is stored in PostgreSQL |
| **Retries & Idempotency** | Prevents duplicate executions |
| **AI Failure Analysis** | LLM reads logs and suggests fixes |
| **Cloud-native** | Runs on Kubernetes |

---

## 🏗 System Architecture
<img width="2574" height="1146" alt="diagram-export-1-3-2026-10_53_55-PM" src="https://github.com/user-attachments/assets/c9bc5b8d-4de3-465d-b266-00f77e71a44b" />



---

## 🧠 Real-World Example (Zomato-Style Order)

User places an order:

```json
{
  "workflowName": "food-order",
  "steps": [
    { "name": "ValidateOrder", "service": "order-service" },
    { "name": "ProcessPayment", "service": "payment-service" },
    { "name": "ReserveInventory", "service": "inventory-service" },
    { "name": "AssignDelivery", "service": "delivery-service" },
    { "name": "SendNotification", "service": "notification-service" }
  ]
}
```


## 🔄 Execution Flow

```
1. Client submits a workflow

2. API saves it in PostgreSQL

3. Workflow Engine emits events to Kafka

4. Workers pick up tasks

5. Each step updates execution state

6. Next step is triggered

7. On failure → AI analyzes logs → retries or reports
```

🧠 AI Failure Analysis

# When a service fails:

PaymentService Error:
"Timeout while connecting to Stripe API"

The AI Agent:

```
Reads logs
Searches similar past failures
Suggests:

“Stripe API timeout. Retry with exponential backoff.”

This enables self-healing distributed systems.

```

## 🛠 Tech Stack
```
Layer	Technology
API Gateway	Java, Spring Boot
Workflow Engine	Spring Boot
Messaging	Kafka
Storage	PostgreSQL
Cache	Redis
Workers	Spring Boot
AI	OpenAI / LLM APIs
Deployment	Docker, Kubernetes
```

## 🗂 Project Structure

``` ai-distributed-workflow-platform
├── api-gateway
├── workflow-engine
├── worker-service
├── order-service
├── payment-service
├── inventory-service
├── delivery-service
├── notification-service
├── ai-log-analyzer
├── docker-compose.yml
└── k8s/
```

## 🔥 Why This Project Matters
```
This is not a CRUD app.

This project demonstrates:

Distributed systems

Event-driven architecture

Cloud-native orchestration

AI-powered observability
```

This is the same architectural pattern used by:
Uber, Netflix, Amazon, Zomato, and PLEXOS Cloud.

---------------------------------------------------------------------
## 🧩 Register / Initialize a Workflow

Use this endpoint to register a workflow definition before executing it.

```POST /workflows ```


This API stores the workflow definition and makes it available for execution.

📌 Request Body (Workflow Definition – JSON)
```
{
  "workflowId": "bd3d7ceb-0856-4b6c-9251-afecd962ffd1",
  "name": "Order Processing Workflow",
  "stages": [
    {
      "stageId": "order-validation",
      "tasks": [
        {
          "taskId": "validate-order",
          "type": "ORDER_VALIDATION",
          "inputs": {
            "orderId": "ORD-1001",
            "userId": "USR-99"
          },
          "retryPolicy": {
            "maxRetries": 2,
            "backoffSeconds": 5
          },
          "timeoutSeconds": 30
        }
      ]
    },
    {
      "stageId": "payment-stage",
      "tasks": [
        {
          "taskId": "process-payment",
          "type": "PAYMENT",
          "inputs": {
            "orderId": "ORD-1001",
            "amount": 499.99,
            "currency": "INR"
          },
          "retryPolicy": {
            "maxRetries": 3,
            "backoffSeconds": 10
          },
          "timeoutSeconds": 60
        }
      ]
    },
    {
      "stageId": "delivery-stage",
      "tasks": [
        {
          "taskId": "assign-delivery",
          "type": "DELIVERY_ASSIGN",
          "inputs": {
            "orderId": "ORD-1001",
            "city": "Bangalore"
          },
          "retryPolicy": {
            "maxRetries": 1
          },
          "timeoutSeconds": 30
        },
        {
          "taskId": "live-tracking",
          "type": "LIVE_TRACKING",
          "inputs": {
            "driverId": "DRV-101",
            "intervalSeconds": 5
          },
          "retryPolicy": {
            "maxRetries": 0
          },
          "timeoutSeconds": 300
        }
      ]
    }
  ]
}
```
## 📸 Postman Request – Register Workflow

<img width="1100" height="821" alt="image" src="https://github.com/user-attachments/assets/27765ea8-01c7-4916-9fad-1d958b6b3618" />


## ▶️ Execute the Workflow

Once the workflow is registered, trigger execution using:

``` POST /executions/start/{workflowId} ```


## Example:

``` POST /executions/start/bd3d7ceb-0856-4b6c-9251-afecd962ffd1 ```
<img width="1760" height="720" alt="image" src="https://github.com/user-attachments/assets/b3c2a66a-4bbd-4e70-832b-72cbce343497" />

DB entries:

<img width="1912" height="910" alt="image" src="https://github.com/user-attachments/assets/7b9cf0bd-3a69-401c-8708-ff9f77b2df0f" />

<img width="1841" height="547" alt="image" src="https://github.com/user-attachments/assets/27c92182-ab8c-414e-a97b-3135c843a223" />


---------------------------------------------------------------------

## 🧑‍💻 Author

Surendra Sharma

Associate Software Engineer – Energy Exemplar

GitHub: https://github.com/nittsurendra

