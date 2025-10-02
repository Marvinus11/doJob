package com.marvinus.doJob.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCandidateResponseDTO {
    private String access_token;
    private Long expires_in;
}
