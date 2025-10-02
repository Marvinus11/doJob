package com.marvinus.doJob.modules.candidate.useCases;

import com.marvinus.doJob.exceptions.JobNotFoundException;
import com.marvinus.doJob.exceptions.UserFoundException;
import com.marvinus.doJob.modules.candidate.entities.ApplyJobEntity;
import com.marvinus.doJob.modules.candidate.repositories.ApplyJobRepository;
import com.marvinus.doJob.modules.candidate.repositories.CandidateRepository;
import com.marvinus.doJob.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserFoundException();
                });

        this.jobRepository.findById(idJob)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });

        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        applyJob = applyJobRepository.save(applyJob);
        return applyJob;

    }

}
