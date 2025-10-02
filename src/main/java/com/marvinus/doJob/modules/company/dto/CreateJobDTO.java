package com.marvinus.doJob.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "Desenvolvedor Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "TotalPass, VR, VA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}
