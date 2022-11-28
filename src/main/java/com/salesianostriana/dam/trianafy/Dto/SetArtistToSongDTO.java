package com.salesianostriana.dam.trianafy.Dto;


import com.salesianostriana.dam.trianafy.model.Artist;
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
