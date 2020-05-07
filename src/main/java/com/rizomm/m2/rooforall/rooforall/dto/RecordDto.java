package com.rizomm.m2.rooforall.rooforall.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordDto {

    Long id;

    private boolean draft;

    @NotBlank(message = "habitatType must not be blank")
    private String habitatType;

    @NotNull(message = "budgetMin cannot be null")
    private Long budgetMin;

    @NotNull(message = "budgetMax cannot be null")
    private Long budgetMax;

    @NotNull(message = "habitationSurface cannot be null")
    private Long habitationSurface;

    @NotBlank(message = "town must not be blank")
    private String town;

}
