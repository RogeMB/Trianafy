package com.salesianostriana.dam.trianafy.model;

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
    private Long id;

    private String title;
    private String album;
    @Column(name = "year_of_song")
    private String year;

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
