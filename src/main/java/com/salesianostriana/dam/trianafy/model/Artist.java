package com.salesianostriana.dam.trianafy.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor @AllArgsConstructor
//@Data
@Setter
@Getter
@Builder
public class Artist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
