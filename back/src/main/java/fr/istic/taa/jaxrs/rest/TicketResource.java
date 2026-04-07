package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.dto.TicketCreateDTO;
import fr.istic.taa.jaxrs.services.TicketService;
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

@Path("tickets")
@Produces({"application/json"})
@Tag(name = "Tickets", description = "Gestion des tickets de concert")
public class TicketResource {

  private final TicketService service = new TicketService();

  @GET
  @Path("/{id}")
  @Operation(
      summary = "Récupérer un ticket par son identifiant",
      description = "Retourne les informations détaillées d'un ticket à partir de son identifiant unique"
  )

      @ApiResponse(
          responseCode = "200",
          description = "Ticket trouvé avec succès",
          content = @Content(schema = @Schema(implementation = Ticket.class))
      )
      @ApiResponse(responseCode = "404", description = "Ticket non trouvé")

  public Ticket getTicketById(
      @Parameter(description = "Identifiant unique du ticket", required = true)
      @PathParam("id") Long id) {
      return service.findOne(id);
  }

  @GET
  @Path("/")
  @Operation(
      summary = "Lister tous les tickets",
      description = "Retourne la liste complète de tous les tickets enregistrés"
  )

      @ApiResponse(
          responseCode = "200",
          description = "Liste de tous les tickets",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = Ticket.class)))
      )

  public List<Ticket> getAllTickets() {
      return service.findAll();
  }

  @POST
  @Path("/")
  @Consumes("application/json")
  @Operation(
      summary = "Acheter un ticket pour un concert",
      description = "Crée un nouveau ticket pour un utilisateur et un concert donnés. "
          + "Le concert doit exister, ne pas être complet, se tenir dans le futur, "
          + "et la place demandée doit être disponible."
  )

      @ApiResponse(responseCode = "201", description = "Ticket créé avec succès")
      @ApiResponse(responseCode = "400", description = "Données invalides (utilisateur inexistant, concert passé)")
      @ApiResponse(responseCode = "404", description = "Concert non trouvé")
      @ApiResponse(responseCode = "409", description = "Place déjà réservée ou concert complet")

  public Response createTicket(
      @RequestBody(
          description = "Informations du ticket à créer",
          required = true,
          content = @Content(schema = @Schema(implementation = TicketCreateDTO.class))
      )
      final @Valid TicketCreateDTO ticket) throws URISyntaxException {
    long id = service.create(ticket);
    URI uri = new URI("/tickets/" + id);
    return Response.created(uri).build();
  }
}
