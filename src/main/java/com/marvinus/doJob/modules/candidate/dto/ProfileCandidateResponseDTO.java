package com.marvinus.doJob.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileCandidateResponseDTO {

    @Schema(example = "João")
    private String name;
    @Schema(example = "joao11")
    private String username;
    @Schema(example = "joao@gmail.com")
    private String email;
    @Schema(example = "Desenvolvedor Java Pleno")
    private String description;
    private UUID id;

}
