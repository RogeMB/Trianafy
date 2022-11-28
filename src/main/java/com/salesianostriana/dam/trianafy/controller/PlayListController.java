package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.*;
import com.salesianostriana.dam.trianafy.model.Playlist;
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
@RequestMapping("/playlist")
@Tag(name = "Playlist", description = "Esta clase implementa Restcontrollers para la entidad Playlist")
public class PlayListController {

    private final PlaylistService playlistServ;

    private final SongService songServ;

    private final ArtistService artistServ;
    private final ListConverterDTO listConverterDTO;


    @Operation(summary = "Este método lista todas las playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado canciones",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                             [
                                                {"id": 12,
                                                "name": "Random",
                                                "numberOfSongs": "4"},
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetListDTO>> getAllPlaylists() {
        if (playlistServ.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<GetListDTO> listOfPL = playlistServ.findAll().stream().map(listConverterDTO::listToDTO).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(listOfPL);
        }
    }


    @Operation(summary = "Este método lista una playlist buscada por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado la playlist buscada",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                               {"id":12,"name":"Random","description":"Una lista muy loca","songs":
                                               [{"id":9,"title":"Enter Sandman","artist":"Metallica","album":"Metallica","year":"1991"},
                                               {"id":8,"title":"Love Again","artist":"Dua Lipa","album":"Future Nostalgia","year":"2021"},
                                               {"id":9,"title":"Enter Sandman","artist":"Metallica","album":"Metallica","year":"1991"},
                                               {"id":11,"title":"Nothing Else Matters","artist":"Metallica","album":"Metallica","year":"1991"}]}
                                                     ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist con ese id",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetOneListDetailsDTO> findOnePlaylist(
            @Parameter(description = "Id de la playlist que se quiera buscar")
            @PathVariable Long id
    ) {
        if (playlistServ.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(listConverterDTO.listDetailsDTO(playlistServ.findById(id).get()));
        }
    }


    @Operation(summary = "Este método crea una nueva playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una nueva playlist",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SetPlayListDTO.class)),
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
    public ResponseEntity<SetPlayListDTO> createOnePlaylist(
            @RequestBody SetPlayListDTO setPLDTO) {
        if (setPLDTO.getName() == null
                || setPLDTO.getDescription() == null
                || setPLDTO.getDescription().isBlank()
                || setPLDTO.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Playlist pl = listConverterDTO.createPlDtotoPlaylist(setPLDTO);
            playlistServ.add(pl);
            setPLDTO.setId(pl.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(setPLDTO);
        }
    }



    @Operation(summary = "Este método edita una playlist si la ha localizado por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado una playlist",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetListDTO.class)),
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
                    description = "No se ha encontrado ninguna playlist con esa ID",
                    content = @Content)
    })
    @PutMapping("/list/{id}")
    public ResponseEntity<GetListDTO> updateOnePlayList (
            @PathVariable Long id,
            @RequestBody SetPlayListDTO setPLDTO) {


        return ResponseEntity.of(playlistServ.findById(id).map(oldPL -> {
            oldPL.setName(setPLDTO.getName());
            oldPL.setDescription(setPLDTO.getDescription());

            return Optional.of(listConverterDTO.listToDTO(playlistServ.add(oldPL)));
                        })
                        .orElse(Optional.empty())
        );
    }


}