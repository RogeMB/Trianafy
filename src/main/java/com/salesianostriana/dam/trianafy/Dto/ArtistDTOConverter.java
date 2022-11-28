package com.salesianostriana.dam.trianafy.Dto;

import com.salesianostriana.dam.trianafy.model.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistDTOConverter {
    public static SetArtistToSongDTO toArtistDto(Artist artist) {
        return SetArtistToSongDTO
                .builder()
                .id(artist.getId())
                .artist(artist.getName())
                .build();
    }
}
