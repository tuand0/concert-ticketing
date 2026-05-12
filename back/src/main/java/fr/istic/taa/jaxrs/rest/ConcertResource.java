package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.enums.StatutConcertEnum;
import fr.istic.taa.jaxrs.dto.ConcertCreateDTO;
import fr.istic.taa.jaxrs.dto.ConcertSearchDTO;
import fr.istic.taa.jaxrs.services.ConcertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("concerts")
@Produces({"application/json"})
@RolesAllowed({"organisateur", "administrateur"})
@Tag(name = "Concerts", description = "Gestion des concerts musicaux")
public class ConcertResource {

    private final ConcertService concertService = new ConcertService();

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
        return concertService.findOne(id);
    }

    @GET
    @Path("/publie")
    @Operation(
            summary = "Rechercher des concerts deja publiés",
            description = "Retourne la liste des concerts correspondant aux critères de recherche fournis en paramètres de requête"
    )
    @Parameter(name = "titre", description = "Titre du concert", in = ParameterIn.QUERY)
    @Parameter(name = "artiste", description = "Nom de l'artiste", in = ParameterIn.QUERY)
    @Parameter(name = "ville", description = "Ville du concert", in = ParameterIn.QUERY)
    @Parameter(name = "lieu", description = "Lieu du concert", in = ParameterIn.QUERY)
    @Parameter(name = "genre", description = "Genre musical", in = ParameterIn.QUERY)
    @Parameter(name = "statut", description = "Statut du concert", in = ParameterIn.QUERY)
    @Parameter(name = "dateMin", description = "Date minimale", in = ParameterIn.QUERY)
    @Parameter(name = "dateMax", description = "Date maximale", in = ParameterIn.QUERY)
    @Parameter(name = "prixMin", description = "Prix minimum", in = ParameterIn.QUERY)
    @Parameter(name = "prixMax", description = "Prix maximum", in = ParameterIn.QUERY)
    @ApiResponse(
            responseCode = "200",
            description = "Liste des concerts correspondant aux critères",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Concert.class)))
    )
    public List<Concert> findConcerts(@Parameter(hidden = true) @Context UriInfo info) {
        ConcertSearchDTO searchDTO = new ConcertSearchDTO(info.getQueryParameters());
        return concertService.searchConcerts(searchDTO);
    }

    @GET
    @Path("/brouillons")
    public Response getDraftConcerts() {
        return Response.ok(
                concertService.findByStatut(StatutConcertEnum.BROUILLON)
        ).build();
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
        long id = concertService.create(concert);
        URI uri = new URI("/concerts/" + id);
        return Response.created(uri).build();
    }

    @PATCH
    @Path("/{concertId}/statut")
    public Response updateConcertStatut(
            @PathParam("concertId") Long concertId,
            @QueryParam("statut") String statut
    ) {
        return Response.ok(
                concertService.updateStatut(concertId, statut)
        ).build();
    }

    @DELETE
    @Path("/{concertId}")
    public Response deleteConcert(@PathParam("concertId") Long concertId) {
        concertService.deleteConcert(concertId);
        return Response.noContent().build();
    }
}
