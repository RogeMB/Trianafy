# TRIANAFY
## API REST con SPRING - Proyecto de prácticas para estudiantes de 2ºDAM.

<img src="https://img.shields.io/badge/Spring--Framework-5.7-green"/> <img src="https://img.shields.io/badge/Apache--Maven-3.8.6-blue"/> <img src="https://img.shields.io/badge/Java-17.0-brightgreen"/>

 <img src="https://niixer.com/wp-content/uploads/2020/11/spring-boot.png" width="500" alt="Spring Logo"/>
 
___
## **Introducción**

Este es un ejercicio práctico para el desarrollo de una API REST en lenguaje Java y manejando diferentes tecnologías en la que destaca **Spring**.

Se ha trabajado sobre la siguiente **documentación:**

* [Baeldung](https://www.baeldung.com/)


También se ha prácticado el manejo de **PostMan**, **Swagger** y la metodología ágil **SCRUM** para el reparto de tareas a través la ramificación de estas con **GitHub**.

Se pueden realizar las siguientes funcionalidades: 
* Listado de Artistas 
* Búsqueda de un artista por Id
* Creación de un artista
* Edición de un artista
* Borrado de un artista
* Listado de canciones
* Búsqueda de una canción por un Id
* Creación de una canción
* Edición de una canción
* Borrado de una canción

---

## **Tecnologías utilizadas** 

Para realizar este proyecto hemos utilizado:

1. [Spring Boot 2.7.5](https://spring.io/) - Dependencias: **Spring-Web**, **JPA**, **H2 Database**, **Sprin-doc Open-api**, **Lombok**
2. [Apache Maven 3.8.6](https://maven.apache.org/)
3. [Postman](https://www.postman.com/)
4. [GitHub](https://github.com/)
5. [springdoc 1.6.13](https://springdoc.org/)
6. [Swagger](https://swagger.io/)



### Ejemplos del Código Usado: 

**JAVA**:
```Java
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

```

**Documentación**

```Java
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
```


---
## **Arranque**



Realiza un *git clone* de la siguiente dirección: 
*https://github.com/RogeMB/Trianafy*

```console
git clone https://github.com/RogeMB/Trianafy.git
```

Dirígete hasta la carpeta:

> cd ./Trianafy/


**Primero** tienes que tener instalado Apache Maven y sería recomendable tener alguna IDE, como **Intellij IDEA** o **VisualStudio Code**

Ejecuta el siguiente comando:
    
    mvn complile
    
    
Ejecuta el siguiente comando:
    
    mvn clean


Ejecuta el comando:

    mvn spring-boot:run
    
    
Si diese algún error, realiza el siguiente comando:  

    mvn dependencies:resolve
    ---> 100% 

___
## **Autor**

Este proyecto ha sido realizado por: 

* [Rogelio Mohigefer Barrera - GITHUB](https://github.com/RogeMB)

Etudiante de 2º Desarrollo de Aplicaciones Multiplataforma, grado 
superior de formación profesional en la ciudad de Sevilla, España.

### **Thump up :+1: And if it was useful for you, star it! :star: :smiley:**

___
## **TODO**

Tareas realizadas y cosas por hacer.

[x] Every PlayList Endpoints
[ ] Fix possible future errors
___


