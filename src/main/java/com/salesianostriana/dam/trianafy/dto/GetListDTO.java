package com.salesianostriana.dam.trianafy.dto;


import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetListDTO {

    private Long id;
    private String name;
    private int numberOfSongs;
/*
    public static GetListDTO of (Playlist pl) {
        return GetListDTO.builder()
                .id(pl.getId())
                .name(pl.getName())
                .numberOfSongs(pl.getSongs().size())
                .build();
    }*/
}
