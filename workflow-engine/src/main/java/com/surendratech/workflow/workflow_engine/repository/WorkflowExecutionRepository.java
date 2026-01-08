package com.surendratech.workflow.workflow_engine.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surendratech.workflow.workflow_engine.entity.WorkflowExecutionEntity;

@Repository
public interface WorkflowExecutionRepository extends JpaRepository<WorkflowExecutionEntity, UUID> {
    Optional<WorkflowExecutionEntity> findByInstanceId(String instanceId);
    List<WorkflowExecutionEntity> findByWorkflowId(String workflowId);
    List<WorkflowExecutionEntity> findByStatus(String status);
}
