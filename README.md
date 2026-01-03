# 🚀 AI-Powered Distributed Workflow Platform  

A **cloud-native, distributed workflow orchestration platform** that executes complex business processes across microservices using **Kafka-driven event orchestration** and **AI-powered failure analysis**.

This system is inspired by how platforms like **Zomato, Flipkart, Uber, and PLEXOS Cloud** coordinate orders, payments, inventory, and compute jobs at massive scale.

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
<img width="2380" height="1556" alt="diagram-export-1-3-2026-10_49_13-PM" src="https://github.com/user-attachments/assets/8e04189c-03d9-452e-9f79-47dfd132ef50" />



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

## 🧑‍💻 Author

Surendra Sharma

Associate Software Engineer – Energy Exemplar

GitHub: https://github.com/nittsurendra

