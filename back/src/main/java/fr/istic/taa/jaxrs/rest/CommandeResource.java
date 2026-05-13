package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.mappers.CommandeMapper;
import fr.istic.taa.jaxrs.services.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Récupérer le panier d'un client",
            description = "Retourne la commande en attente du client, qui représente son panier actuel. " +
                    "Si aucune commande en attente n'existe, un panier vide peut être créé et retourné."
    )
    public Response getCart(@QueryParam("clientId") Long clientId) {
        return Response.ok(
                commandeMapper.toDTO(commandeService.getCart(clientId))
        ).build();
    }

    @GET
    @Path("/client/{clientId}")
    @Operation(
            summary = "Récupérer les commandes d'un client",
            description = "Retourne toutes les commandes associées à un client, incluant les commandes en attente, payées ou annulées."
    )
    public Response getCommandesByClient(@PathParam("clientId") Long clientId) {
        return Response.ok(
                commandeMapper.toDTOList(
                        commandeService.findByClientId(clientId)
                )
        ).build();
    }

    @POST
    @Path("/cart/concerts/{concertId}")
    @Operation(
            summary = "Créer un nouveau commande (add concert to my-cart)",
            description = "Add a concert to client my-cart " +
                    "Client & concert must be existed."
    )
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
    @Operation(
            summary = "Client confirm purchase",
            description = "Client confirm payer ticket in my-cart"
    )
    public Response confirmCart(@QueryParam("clientId") Long clientId) {
        return Response.ok(
                commandeMapper.toDTO(
                        commandeService.confirmCart(clientId)
                )
        ).build();
    }
}
