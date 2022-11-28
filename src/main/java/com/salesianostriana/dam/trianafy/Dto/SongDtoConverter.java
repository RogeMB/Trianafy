package com.salesianostriana.dam.trianafy.Dto;


import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongDtoConverter {


    public Song dtoToSong (SetSongDTO SDto) {
        return Song
            .builder().title(SDto.getTitle()).album(SDto.getAlbum()).year(SDto.getYear()).build();
    }


    public static GetSongDTO toSongDto (Song s) {
        String artistName="";

        if(s.getArtist() != null) {
            artistName = s.getArtist().getName();
        }
        return GetSongDTO.builder()
                .id(s.getId())
                .title(s.getTitle())
                .artist(artistName)
                .year(s.getYear())
                .album(s.getAlbum())
                .build();
    }

}
