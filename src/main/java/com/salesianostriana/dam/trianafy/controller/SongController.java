package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.Dto.GetSongDTO;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/song")
@Tag(name = "Song", description = "Esta clase implementa Restcontrollers para la entidad Song")
public class SongController {

    private final SongService songServ;


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
        List<Song> songsList = songServ.findAll();
        if(songsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(songsList.stream()
                .map(GetSongDTO::of)
                .collect(Collectors.toList())
        );
    }


}
