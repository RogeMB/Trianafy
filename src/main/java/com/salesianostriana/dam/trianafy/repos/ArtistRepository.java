package com.salesianostriana.dam.trianafy.repos;

import com.salesianostriana.dam.trianafy.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
