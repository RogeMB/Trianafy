package com.salesianostriana.dam.trianafy.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
@Builder
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Song> songs = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Song> songs2 = new HashSet<>();


    public void addSong(Song song) {
        songs.add(song);
    }
    public void deleteSong(Song song) {
        songs.remove(song);
    }

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
