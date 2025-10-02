package com.marvinus.doJob.modules.candidate.controllers;

import com.marvinus.doJob.modules.candidate.entities.CandidateEntity;
import com.marvinus.doJob.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.marvinus.doJob.modules.candidate.useCases.ApplyJobCandidateUseCase;
import com.marvinus.doJob.modules.candidate.useCases.CreateCandidateUseCase;
import com.marvinus.doJob.modules.candidate.useCases.ProfileCandidateUseCase;
import com.marvinus.doJob.modules.company.entities.JobEntity;
import com.marvinus.doJob.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;


    @PostMapping("/")
    @Operation(summary = "Cadastro do candidato", description = "Função responsável por cadastrar informações do perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User exists")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity ){
        try{
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Função responsável por buscar informações do perfil do candidato")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    public ResponseEntity<Object> get(HttpServletRequest request) {

        var candidateId = request.getAttribute("candidate_id");

        try {
            var profile = this.profileCandidateUseCase
                    .execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(profile);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name = "Candidato", description = "Informações do candidato")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Função responsável por listar vagas por filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Inscrição do candidato para vafa", description = "Função responsável por realizar a inscrição do candidato na vaga")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        }catch (Exception e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
