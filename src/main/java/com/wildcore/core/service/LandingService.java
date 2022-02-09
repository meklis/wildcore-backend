package com.wildcore.core.service;

import com.wildcore.core.model.BetaRequest;
import com.wildcore.core.repository.BetaRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LandingService {
    private final BetaRequestRepository repository;

    public BetaRequest requestBeta(BetaRequest request) {
        return repository.save(request);
    }

}
