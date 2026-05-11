package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.mappers.CommandeMapper;
import fr.istic.taa.jaxrs.services.CommandeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/commandes")
@Produces("application/json")
@Consumes("application/json")
@Tag(name = "Commande", description = "Gestion des commandes")
public class CommandeResource {

    private final CommandeService commandeService = new CommandeService();
    private final CommandeMapper commandeMapper = new CommandeMapper();

    @GET
    @Path("/cart")
    public Response getCart(@QueryParam("clientId") Long clientId) {
        return Response.ok(
                commandeMapper.toDTO(commandeService.getCart(clientId))
        ).build();
    }

    @GET
    @Path("/client/{clientId}")
    public Response getCommandesByClient(@PathParam("clientId") Long clientId) {
        return Response.ok(
                commandeMapper.toDTOList(
                        commandeService.findByClientId(clientId)
                )
        ).build();
    }

    @POST
    @Path("/cart/concerts/{concertId}")
    public Response addConcertToCart(
            @QueryParam("clientId") Long clientId,
            @PathParam("concertId") Long concertId
    ) {
        return Response.ok(
                commandeMapper.toDTO(
                        commandeService.addConcertToCart(clientId, concertId)
                )
        ).build();
    }

    @DELETE
    @Path("/cart/tickets/{ticketId}")
    public Response removeTicketFromCart(
            @QueryParam("clientId") Long clientId,
            @PathParam("ticketId") Long ticketId
    ) {
        return Response.ok(
                commandeMapper.toDTO(
                        commandeService.removeTicket(clientId, ticketId)
                )
        ).build();
    }

    @POST
    @Path("/cart/confirm")
    public Response confirmCart(@QueryParam("clientId") Long clientId) {
        return Response.ok(
                commandeMapper.toDTO(
                        commandeService.confirmCart(clientId)
                )
        ).build();
    }
}
