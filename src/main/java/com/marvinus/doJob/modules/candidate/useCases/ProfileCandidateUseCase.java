package com.marvinus.doJob.modules.candidate.useCases;

import com.marvinus.doJob.exceptions.UserNotFoundException;
import com.marvinus.doJob.modules.candidate.entities.CandidateEntity;
import com.marvinus.doJob.modules.candidate.repositories.CandidateRepository;
import com.marvinus.doJob.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(UserNotFoundException::new);
        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .id(candidate.getId())
                .email(candidate.getEmail())
                .name(candidate.getName())
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .build();
        return candidate;
    }
}
