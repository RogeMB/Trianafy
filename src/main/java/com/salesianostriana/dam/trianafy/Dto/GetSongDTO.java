package com.salesianostriana.dam.trianafy.Dto;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSongDTO {

    private Long id;
    private String title;
    private String artist;
    private String album;
    private String year;

    /*public static GetSongDTO of (Song s) {
        String artistName="NoNameKnown";

        if(s.getArtist() != null) {
            artistName = s.getArtist().getName();
        }
        return GetSongDTO
                .builder()
                .id(s.getId())
                .title(s.getTitle())
                .artist(artistName)
                .album(s.getAlbum())
                .year(s.getYear())
                .build();
    }*/

}
