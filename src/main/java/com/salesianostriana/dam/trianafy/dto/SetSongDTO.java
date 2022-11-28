package com.salesianostriana.dam.trianafy.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetSongDTO {
    private String title;
    private String year;
    private String album;
    private Long idArtist;

}
