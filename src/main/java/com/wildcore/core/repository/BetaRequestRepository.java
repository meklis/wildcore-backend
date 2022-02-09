package com.wildcore.core.repository;

import com.wildcore.core.model.BetaRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetaRequestRepository extends CrudRepository<BetaRequest, Integer> {

    List<BetaRequest> findAllBySentFalse();
}
