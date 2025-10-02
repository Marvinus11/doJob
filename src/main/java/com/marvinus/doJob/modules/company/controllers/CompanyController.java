package com.marvinus.doJob.modules.company.controllers;

import com.marvinus.doJob.modules.company.entities.CompanyEntity;
import com.marvinus.doJob.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Tag(name = "Empresa", description = "Informações das empresas")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
        try {
            var result = this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
