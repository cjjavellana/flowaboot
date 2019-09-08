package com.cjavellana.flowable.controller;

import com.cjavellana.flowable.models.CreateAssetRequest;
import com.cjavellana.flowable.services.AssetWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssetWorkflowController {

    private final AssetWorkflowService service;

    @Autowired
    public AssetWorkflowController(AssetWorkflowService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public void submit(@RequestBody CreateAssetRequest createAssetRequest) {
        service.startProcess(createAssetRequest);
    }

}
