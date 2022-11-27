package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistServ;


    @Operation(summary = "Este método lista todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado artistas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "name": "Dua Lipa"},
                                                {"id": 2, "name": "Joaquín Sabina"}          
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún artista",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity <List<Artist>> findAllArtist() {
        if(artistServ.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.ok(artistServ.findAll());
        }
    }

    @Operation(summary = "Este método crea un nuevo artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nuevo artista",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "name": "Muse"},    
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido bien los datos del artista",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Artist> createOneArtist(@RequestBody @NotNull Artist artist) {
        if(artist.getName() == null || artist.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(artistServ
                            .add(artist));
        }
    }



}
