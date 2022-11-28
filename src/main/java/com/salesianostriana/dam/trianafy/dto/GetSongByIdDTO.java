package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetSongByIdDTO {
    private Long id;
    private String title;
    private String album;
    private SetArtistToSongDTO artist;
    private String year;
}
