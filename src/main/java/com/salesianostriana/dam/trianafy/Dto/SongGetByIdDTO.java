package com.salesianostriana.dam.trianafy.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongGetByIdDTO {
    private Long id;
    private String title;
    private String album;
    private SetArtistToSongDTO artist;
    private String year;
}
