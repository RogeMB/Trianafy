package com.salesianostriana.dam.trianafy.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetPlayListDTO {

    private Long id;
    private String name;
    private String  description;

}
