package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.service.ArtistService;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/artist")
@Tag(name = "Artists", description = "Esta clase implementa un CRUD de Restcontrollers para la entidad Artists")
public class ArtistController {

    private final ArtistService artistServ;
    private final SongService songServ;


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


    @Operation(summary = "Este método busca un artista por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al artista que buscaba",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 2, "name": "Joaquín Sabina"}          
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ese artista",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Artist> findOneArtistById(
            @Parameter(description = "Id del Artista que se quiera buscar")
            @PathVariable Long id
    ) {
        if (artistServ.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.of(artistServ.findById(id));
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
                                                {"id": 13, "name": "Muse"},    
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido bien los datos del artista",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Artist> createOneArtist(
            @RequestBody @NotNull Artist artist) {
        if(artist.getName() == null || artist.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(artistServ
                            .add(artist));
        }
    }


    @Operation(summary = "Este método edita un artista localizado por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado un nuevo artista",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "name": "RHCP"},    
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido bien los datos del artista",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún artista con ese ID",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Artist> editOneArtistbyId (
            @Parameter(description = "Id del Artista que se quiera editar")
            @RequestBody @NotNull Artist artist,
            @PathVariable Long id) {
        if(artistServ.findById(id).isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else if(artist.getName() ==null || artist.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            return ResponseEntity.of(artistServ.findById(id).map(oldArt -> {
                oldArt.setName(artist.getName());
                artistServ.edit(artist);
                return Optional.of(artistServ.edit(oldArt));
                })
                    .orElse(Optional.empty())
            );
        }
     }


    @Operation(summary = "Este método elimina un artista localizado por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Artista eliminado correctamente",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneArtistById (
            @Parameter(description = "Id del Artista que se quiera eliminar")
            @PathVariable Long id) {
        if(artistServ.findById(id).isPresent()){
            songServ.findByArtist(artistServ.findById(id))
                    .stream()
                    .forEach(song -> song.setArtist(null));
            artistServ.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
