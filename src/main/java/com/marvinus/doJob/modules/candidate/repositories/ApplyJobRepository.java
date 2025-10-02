package com.marvinus.doJob.modules.candidate.repositories;

import com.marvinus.doJob.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
