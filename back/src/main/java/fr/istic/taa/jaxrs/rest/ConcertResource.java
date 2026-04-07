package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.dto.ConcertCreateDTO;
import fr.istic.taa.jaxrs.services.ConcertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("concerts")
@Produces({"application/json"})
@Tag(name = "Concerts", description = "Gestion des concerts musicaux")
public class ConcertResource {

    private final ConcertService service = new ConcertService();

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Récupérer un concert par son identifiant",
            description = "Retourne les informations détaillées d'un concert à partir de son identifiant unique"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Concert trouvé avec succès",
            content = @Content(schema = @Schema(implementation = Concert.class))
    )
    @ApiResponse(responseCode = "404", description = "Concert non trouvé")
    public Concert getConcertById(
            @Parameter(description = "Identifiant unique du concert", required = true)
            @PathParam("id") Long id) {
        return service.findOne(id);
    }

    @GET
    @Path("/")
    @Operation(
            summary = "Lister tous les concerts",
            description = "Retourne la liste complète de tous les concerts disponibles"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste de tous les concerts",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Concert.class)))
    )
    public List<Concert> getConcerts() {
        return service.findAll();
    }

    @POST
    @Path("/")
    @Consumes("application/json")
    @Operation(
            summary = "Créer un nouveau concert",
            description = "Crée un nouveau concert. L'organisateur référencé doit exister, "
                    + "la capacité doit être positive et la date doit être dans le futur."
    )
    @ApiResponse(responseCode = "201", description = "Concert créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides ou règles métier non respectées (organisateur inexistant, capacité nulle, date passée)")
    public Response createConcert(
            @RequestBody(
                    description = "Informations du concert à créer",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ConcertCreateDTO.class))
            ) final @Valid ConcertCreateDTO concert) throws URISyntaxException {
        long id = service.create(concert);
        URI uri = new URI("/concerts/" + id);
        return Response.created(uri).build();
    }
}
