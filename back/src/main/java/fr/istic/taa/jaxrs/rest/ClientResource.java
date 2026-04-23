package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.dto.ClientCreateDTO;
import fr.istic.taa.jaxrs.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("clients")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientResource {

    private final ClientService service = new ClientService();

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Récupérer un client par son identifiant",
            description = "Retourne les informations d'un client à partir de son identifiant"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Client trouvé",
            content = @Content(schema = @Schema(implementation = Client.class))
    )
    @ApiResponse(responseCode = "404", description = "Client non trouvé")
    public Client getClientById(@PathParam("id") Long id) {
        return service.findOne(id);
    }

    @GET
    @Path("/")
    @Operation(
            summary = "Récupérer tous les clients",
            description = "Retourne la liste de tous les clients"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des clients",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Client.class)))
    )
    public List<Client> getAllClients() {
        return service.findAll();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Créer un client",
            description = "Crée un nouveau client"
    )
    @ApiResponse(responseCode = "201", description = "Client créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public Response createClient(
            @RequestBody(
                    description = "Informations du client à créer",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClientCreateDTO.class))
            )
            final @Valid ClientCreateDTO dto
    ) throws URISyntaxException {

        long id = service.create(dto);
        URI uri = new URI("/clients/" + id);
        return Response.created(uri).build();
    }
}