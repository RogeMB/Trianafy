package com.salesianostriana.dam.trianafy.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.dto.SongDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
@Builder
public class Song {

    @Id
    @GeneratedValue
    @JsonView({SongDTO.NormalSong.class, SongDTO.SongArtist.class})
    private Long id;

    @JsonView({SongDTO.NormalSong.class, SongDTO.SongArtist.class})
    private String title;

    @JsonView({SongDTO.NormalSong.class, SongDTO.SongArtist.class})
    private String album;

    @JsonView({SongDTO.NormalSong.class, SongDTO.SongArtist.class})
    @Column(name = "year_of_song")
    private String year;

    @JsonView(SongDTO.NormalSong.class)
    @ManyToOne(fetch = FetchType.EAGER)
    private Artist artist;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
