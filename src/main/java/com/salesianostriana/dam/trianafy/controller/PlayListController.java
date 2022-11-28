package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.GetListDTO;
import com.salesianostriana.dam.trianafy.dto.GetSongDTO;
import com.salesianostriana.dam.trianafy.dto.ListConverter;
import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
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
@RequestMapping("/playlist")
@Tag(name = "Playlist", description = "Esta clase implementa Restcontrollers para la entidad Playlist")
public class PlayListController {

    private final PlaylistService playlistServ;

    private final SongService songServ;

    private final ArtistService artistServ;
    private final ListConverter listConverter;



    @Operation(summary = "Este método lista todas las playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado canciones",
                    content = { @Content(mediaType = "application/json",
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
        if(playlistServ.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            List<GetListDTO> listOfPL = playlistServ.findAll().stream().map(listConverter::listToDTO).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(listOfPL);
        }
    }

}
