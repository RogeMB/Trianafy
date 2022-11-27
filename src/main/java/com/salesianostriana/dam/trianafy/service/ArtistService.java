package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository repository;


    public Artist add(Artist artist) {
        return repository.save(artist);
    }

    public Optional<Artist> findById(Long id) {
        return repository.findById(id);
    }

    public List<Artist> findAll() {
        return repository.findAll();
    }

    public Artist edit(Artist artist) {
        return repository.save(artist);
    }

    public void delete(Artist artist) {
        repository.delete(artist);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
