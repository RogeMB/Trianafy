package com.salesianostriana.dam.trianafy;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestModel {

    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;

    @PostConstruct
    public void run() {

        Artist a1 = Artist.builder()
                .name("Joaquín Sabina")
                .build();

        Artist a2 = Artist.builder()
                .name("Dua Lipa")
                .build();

        Artist a3 = Artist.builder()
                .name("Metallica")
                .build();

        List<Artist> artistList = List.of(a1, a2, a3);

        artistList.forEach(artistService::add);

        Song s1a1 = Song.builder()
                .album("19 días y 500 noches")
                .artist(a1)
                .year("1999")
                .title("19 días y 500 noches")
                .build();

        Song s2a1 = Song.builder()
                .album("19 días y 500 noches")
                .artist(a1)
                .year("1999")
                .title("Donde habita el olvido")
                .build();

        Song s3a1 = Song.builder()
                .album("19 días y 500 noches")
                .artist(a1)
                .year("1999")
                .title("A mis cuarenta y diez")
                .build();

        Song s1a2 = Song.builder()
                .album("Future Nostalgia")
                .artist(a2)
                .year("2019")
                .title("Don't Start Now")
                .build();

        Song s2a2 = Song.builder()
                .album("Future Nostalgia")
                .artist(a2)
                .year("2021")
                .title("Love Again")
                .build();

        Song s1a3 = Song.builder()
                .album("Metallica")
                .artist(a3)
                .year("1991")
                .title("Enter Sandman")
                .build();

        Song s2a3 = Song.builder()
                .album("Metallica")
                .artist(a3)
                .year("1991")
                .title("Unforgiven")
                .build();

        Song s3a3 = Song.builder()
                .album("Metallica")
                .artist(a3)
                .year("1991")
                .title("Nothing Else Matters")
                .build();

        List<Song> songList = List.of(
                s1a1, s2a1, s3a1,
                s1a2, s2a2,
                s1a3, s2a3, s3a3
        );

        songList.forEach(songService::add);

        Playlist p1 = Playlist.builder()
                .name("Random")
                .description("Una lista muy loca")
                .build();

        playlistService.add(p1);

        p1.addSong(s1a3);
        p1.addSong(s2a2);
        p1.addSong(s1a3);
        p1.addSong(s3a3);

        playlistService.edit(p1);

        System.out.println(p1);








    }

}
