package com.wildcore.core.controller;

import com.wildcore.core.model.BetaRequest;
import com.wildcore.core.service.LandingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/landing")
@AllArgsConstructor
public class LandingController {
    private final LandingService service;

    @PostMapping("/request-beta")
    public BetaRequest submit(@RequestBody BetaRequest request) {
        return service.requestBeta(request);
    }
}
