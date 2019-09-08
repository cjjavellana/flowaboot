package com.cjavellana.flowable.services;

import com.cjavellana.flowable.models.CreateAssetRequest;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AssetWorkflowService {

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    @Autowired
    public AssetWorkflowService(RuntimeService runtimeService,
                                TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @Transactional
    public void startProcess(CreateAssetRequest assetRequest) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("isin", assetRequest.getIsin());
        variables.put("description", assetRequest.getDescription());
        variables.put("action", assetRequest.getAction());
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("SimpleMakerCheckerWorkflow", variables);

        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        taskService.complete(task.getId());
    }
}
