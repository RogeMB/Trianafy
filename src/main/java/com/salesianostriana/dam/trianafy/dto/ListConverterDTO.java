package com.salesianostriana.dam.trianafy.dto;


import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class ListConverterDTO {

    private final SongDtoConverter songDtoConverterngDto;
    public GetListDTO listToDTO (Playlist pl) {
        return GetListDTO.builder()
                .id(pl.getId())
                .name(pl.getName())
                .numberOfSongs(pl.getSongs().size())
                .build();
    }

    public GetOneListDetailsDTO listDetailsDTO (Playlist pl) {
        return GetOneListDetailsDTO.builder()
                .id(pl.getId())
                .name(pl.getName())
                .description(pl.getDescription())
                .songs(converRepons(pl.getSongs()))
                .build();
    }

    public List<GetSongDTO> converRepons(List<Song> songList) {
        List<GetSongDTO> songResp = new ArrayList<>();
        songList.stream().forEach(s -> {
            songResp.add(songDtoConverterngDto.toSongDto(s));
        });
        return songResp;
    }

    public Playlist createPlDtotoPlaylist (SetPlayListDTO st) {
        return new Playlist(
                st.getName(),
                st.getDescription()
        );
    }

}
