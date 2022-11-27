package com.salesianostriana.dam.trianafy.Dto;

import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSongDTO {

    private String title;
    private Long artistId;
    private String album;
    private String year;

}
