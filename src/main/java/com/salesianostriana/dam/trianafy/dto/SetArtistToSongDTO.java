package com.salesianostriana.dam.trianafy.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetArtistToSongDTO {

    private Long id;
    private String artist;
}
