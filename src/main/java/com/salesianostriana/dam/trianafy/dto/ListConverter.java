package com.salesianostriana.dam.trianafy.dto;


import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class ListConverter {
    public GetListDTO listToDTO (Playlist pl) {
        return GetListDTO.builder()
                .id(pl.getId())
                .name(pl.getName())
                .numberOfSongs(pl.getSongs().size())
                .build();
    }

}
