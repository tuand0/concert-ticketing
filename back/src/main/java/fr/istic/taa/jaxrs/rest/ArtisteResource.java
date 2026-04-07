package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.dto.ArtisteCreateDTO;
import fr.istic.taa.jaxrs.dto.ArtisteSearchDTO;
import fr.istic.taa.jaxrs.services.ArtisteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("artistes")
@Produces({"application/json"})
@Tag(name = "Artistes", description = "Gestion des artistes musicaux")
public class ArtisteResource {

    private final ArtisteService service = new ArtisteService();

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Récupérer un artiste par son identifiant",
            description = "Retourne les informations détaillées d'un artiste à partir de son identifiant unique"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Artiste trouvé avec succès",
            content = @Content(schema = @Schema(implementation = Artiste.class))
    )
    @ApiResponse(responseCode = "404", description = "Artiste non trouvé")
    public Artiste getArtisteById(
            @Parameter(description = "Identifiant unique de l'artiste", required = true)
            @PathParam("id") Long id) {
        return service.findOne(id);
    }

    @GET
    @Path("/")
    @Operation(
            summary = "Rechercher des artistes",
            description = "Retourne la liste des artistes correspondant aux critères de recherche fournis en paramètres de requête"
    )
    @Parameter(name = "nomScene", description = "Nom de scène de l'artiste", in = ParameterIn.QUERY)
    @Parameter(name = "nom", description = "Nom de famille de l'artiste", in = ParameterIn.QUERY)
    @Parameter(name = "prenom", description = "Prénom de l'artiste", in = ParameterIn.QUERY)
    @Parameter(name = "nationalite", description = "Nationalité de l'artiste", in = ParameterIn.QUERY)
    @Parameter(name = "popularite", description = "Niveau de popularité de l'artiste", in = ParameterIn.QUERY
            , schema = @Schema(type = "integer"))
    @ApiResponse(
            responseCode = "200",
            description = "Liste des artistes correspondant aux critères",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Artiste.class)))
    )
    public List<Artiste> findArtistes(@Parameter(hidden = true) @Context UriInfo info) {
        ArtisteSearchDTO searchDTO = new ArtisteSearchDTO(info.getQueryParameters());
        return service.searchArtistes(searchDTO);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Créer un nouvel artiste",
            description = "Crée un nouvel artiste. Le nom de scène et la date de naissance sont obligatoires, la popularité doit être comprise entre 0 et 100 et la nationalité doit être un code ISO 3166-1 alpha-2."
    )
    @ApiResponse(responseCode = "201", description = "Artiste créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides (champ manquant ou contrainte de validation non respectée)")
    public Response createArtiste(
            @RequestBody(
                    description = "Informations de l'artiste à créer",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ArtisteCreateDTO.class))
            ) final @Valid ArtisteCreateDTO artiste) throws URISyntaxException {
        long id = service.create(artiste);
        URI uri = new URI("/artistes/" + id);
        return Response.created(uri).build();
    }
}
