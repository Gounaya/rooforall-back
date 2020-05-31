package com.rizomm.m2.rooforall.rooforall.dto;

import com.rizomm.m2.rooforall.rooforall.enums.RecordStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseDto {

    private Long id;

    @NotBlank(message = "description must not be blank")
    private String description;

    @NotNull(message = "price cannot be null")
    private Long price;

    @NotNull(message = "surface must not be null")
    private Long surface;

    @NotNull(message = "rooms must not be null")
    private int rooms;

    @NotBlank(message = "town must not be blank")
    private String town;

    @NotBlank(message = "address must not be blank")
    private String address;

}
