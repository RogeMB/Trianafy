package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.Dto.GetSongDTO;
import com.salesianostriana.dam.trianafy.Dto.SetSongDTO;
import com.salesianostriana.dam.trianafy.Dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.Dto.SongGetByIdDTO;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/song")
@Tag(name = "Song", description = "Esta clase implementa Restcontrollers para la entidad Song")
public class SongController {

    private final SongService songServ;
    private final ArtistService artistServ;

    private final SongDtoConverter songDtoConverter;

    private final PlaylistService playlistServ;


    @Operation(summary = "Este método lista todas las canciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado canciones",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetSongDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 7,
                                                "title": "Don't Start Now",
                                                "artist": "Dua Lipa",
                                                "album": "Future Nostalgia",
                                                "year": "2019"},
                                                
                                                {"id": 8,
                                                "title": "Love Again",
                                                "artist": "Dua Lipa",
                                                "album": "Future Nostalgia",
                                                "year": "2021"},
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetSongDTO>> findAllSongs() {
        if(songServ.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            List<GetSongDTO> songsList = songServ.findAll().stream().map(SongDtoConverter::toSongDto).collect(Collectors.toList());
            return ResponseEntity.ok(songsList);
        }

    }


    @Operation(summary = "Este método lista una canción buscada su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado la canción buscada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SongGetByIdDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 8,
                                                "title": "Love Again",
                                                "artist": "Dua Lipa",
                                                "album": "Future Nostalgia",
                                                "year": "2021"},
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción con ese id",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<SongGetByIdDTO> findOneSong(
            @Parameter(description = "Id de la canción que se quiera buscar")
            @PathVariable Long id
    ) {
        if (songServ.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.ok(songDtoConverter.songDtoByID(songServ.findById(id).get()));
        }
    }



    @Operation(summary = "Este método crea una nueva canción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nueva canción",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SetSongDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                           [
                                               {"id": 18,
                                                "title": "Bliss",
                                                "artist": "Muse",
                                                "album": "Origin of Simmetry",
                                                "year": "2001"},
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido correctamente los datos de la canción",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<GetSongDTO> createOneSong (@RequestBody SetSongDTO setSdto) {
        if(setSdto.getIdArtist() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Song s = songDtoConverter.dtoToSong(setSdto);
        artistServ.findById(setSdto.getIdArtist()).ifPresent(s::setArtist);

        if(s.getTitle().isBlank()
                || s.getYear().isBlank()
                || s.getAlbum().isBlank()
                || s.getArtist() == null
        ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        else {
            return ResponseEntity.status(HttpStatus.CREATED).body(songDtoConverter.toSongDto(songServ.add(s)));

        }
    }


    @Operation(summary = "Este método edita una canción si la ha localizado por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado un nuevo artista",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SetSongDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                               {"id": 8,
                                                "title": "Love Again and again and again",
                                                "artist": "Dua Lipa",
                                                "album": "Past Nostalgia",
                                                "year": "2021"},
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido bien los datos requeridos",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción con esa ID",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetSongDTO> editOneSong (
            @Parameter(description = "Id de la canción que se quiera editar")
            @RequestBody SetSongDTO SetDto,
            @PathVariable Long id
    ) {

        if (SetDto.getIdArtist() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Song s = songDtoConverter.dtoToSong(SetDto);
        Artist a = artistServ.findById(SetDto.getIdArtist()).orElse(null);
        s.setArtist(a);

        if(songServ.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else if (s.getAlbum() == null || s.getAlbum().isBlank()
                || s.getYear() == null || s.getYear().isBlank()
                || s.getTitle() == null || s.getTitle().isBlank()
                || s.getArtist() == null
                ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            return ResponseEntity.of(songServ.findById(id).map(oldSong -> {
                oldSong.setTitle(s.getTitle());
                oldSong.setYear(s.getYear());
                oldSong.setAlbum(s.getAlbum());
                oldSong.setArtist(a);
                return Optional.of(songDtoConverter.toSongDto(songServ.edit(oldSong)));
            }).orElse(Optional.empty())
            );
        }
    }

    @Operation(summary = "Este método elimina una canción y sus repeticiones localizadas por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Canción eliminada correctamente",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSongByid (
            @Parameter(description = "Id de la canción que desea borrar")
            @PathVariable Long id
    ) {
        if(songServ.findById(id).isPresent()) {
            Song s = songServ.findById(id).get();
            playlistServ.findAll()
                    .stream()
                    .filter(playlist -> playlist.getSongs().contains(s)).forEach(playlist -> {
                        while (playlist.getSongs().contains(s)){
                            playlist.deleteSong(s);
                        }
                        playlistServ.edit(playlist);
                    });

            songServ.delete(s);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
